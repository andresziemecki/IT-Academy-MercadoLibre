package controllers

import (
	"github.com/gin-gonic/gin"
)

var ApiCB = NewCircuitBreaker( 8000, 2)

func GetResultFromAPI (c *gin.Context){

	ApiCB.call(c)
/*

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



response ,apiErr := services.GetResultChannel(id)
if err != nil {
	c.JSON(apiErr.Status, apiErr)
	return
}

c.JSON(http.StatusOK, response)
 */

}






