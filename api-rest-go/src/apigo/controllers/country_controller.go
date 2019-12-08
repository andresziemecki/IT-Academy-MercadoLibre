package controllers

import (
	"github.com/gin-gonic/gin"
	"../services"
	"net/http"
)

const (
	paramCountryID = "countryID"
)

// la idea es q haga las resquest

func GetCountryFromAPI (c *gin.Context){
	countryID := c.Param(paramCountryID)
	response ,err := services.GetCountry(countryID)
	if err != nil {
		c.JSON(err.Status, err)
		return
	}

	c.JSON(http.StatusOK, response)
}