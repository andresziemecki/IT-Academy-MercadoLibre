package controllers

import (
	"net/http"
	"../utils"
	"io/ioutil"
	"time"
	"../services"
	"strconv"
	"github.com/gin-gonic/gin"
	"fmt"
)

type StateBreaker struct {
	OPEN chan bool
	HO chan bool
	CLOSE chan bool
}

type CircuitBreaker struct{
	StateChannel StateBreaker
	TimeOutMilli int // How much time it will be in open mode before to be in half open
	NumberErrors int // the numbers of errors which the cb has recived before pass the threshold
	FailureThreshold int // Number of errors that will pass from state Close to Open
	TimeOut bool // channel that will comunicate to the circuit breaker when the timeout happens
}

// Constructor of the circuit breaker
func NewCircuitBreaker(TimeOutMilli int, FailureThreshold int) *CircuitBreaker{

	cb := &CircuitBreaker{
		StateChannel: StateBreaker{
			OPEN: make(chan bool,1),
			HO: make(chan bool,1),
			CLOSE: make(chan bool,1),
		},
		TimeOutMilli:TimeOutMilli,
		NumberErrors: 0,
		FailureThreshold: FailureThreshold,
		TimeOut : false,
	}

	return cb
}

func (cb *CircuitBreaker) call (c *gin.Context) {

	userID :=	c.Param(paramUserID)
	id, err := strconv.Atoi(userID)
	if err != nil {
		apiErr := &utils.ApiError{
			Message: err.Error(),
			Status: http.StatusBadRequest,
		}
		c.JSON(apiErr.Status, apiErr)
		return
	}

	cb.setState() // put the correct state of the circuit breaker before use it

	// Check section of circuit breaker
	select{
		case <- cb.StateChannel.OPEN:
			fmt.Println("Server is OPEN")
			apiErr := &utils.ApiError{
				Message: "Wait, server is down trying to recover",
				Status: 500,
			}
			c.JSON(apiErr.Status, apiErr)
			return

		case <- cb.StateChannel.CLOSE:
			fmt.Println("Server is CLOSE")
			response ,apiErr := services.GetResultChannel(id)
			if apiErr != nil {
				cb.recordFailure()
				c.JSON(apiErr.Status, apiErr)
				return
			}
			cb.reset()
			c.JSON(http.StatusOK, response)
			return

		case <- cb.StateChannel.HO:
			fmt.Println("Server is HALF-OPEN")
			apiErr := &utils.ApiError{
				Message: "Wait, Trying to reconnect",
				Status: 500,
			}
			c.JSON(apiErr.Status, apiErr)
			return
	}

}

func (cb *CircuitBreaker) setState () {
	fmt.Println("setState function...")
	if cb.NumberErrors >= cb.FailureThreshold {
		if cb.TimeOut {
			cb.StateChannel.HO <- true
		} else {
			cb.StateChannel.OPEN <- true
		}
	} else {
		cb.StateChannel.CLOSE <- true
	}
}

func (cb *CircuitBreaker) TryPin () *utils.ApiError{

	cb.TimeOut = true

	fmt.Println("TryPin function...")
	response, err := http.Get(utils.UrlPin)

	cb.TimeOut = false

	if err != nil {
		return &utils.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}

	_ , err = ioutil.ReadAll(response.Body)
	if err != nil {
		return &utils.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	} else {
		cb.reset()
		return nil
	}

}


func (cb *CircuitBreaker) reset(){
	fmt.Println("reset function")
	cb.NumberErrors = 0
	return
}

func (cb *CircuitBreaker) recordFailure(){
	fmt.Println("recordFailure function ...")
	cb.NumberErrors++
	if cb.NumberErrors >= cb.FailureThreshold{
		go cb.TurnOnTimeOut()
	}
	return

}


func (cb * CircuitBreaker) TurnOnTimeOut (){
	fmt.Println("TurnOnTimeOut function")
	time.Sleep(time.Millisecond * time.Duration(cb.TimeOutMilli))
	if err := cb.TryPin(); err != nil{
		cb.TurnOnTimeOut()
	}
	return
}