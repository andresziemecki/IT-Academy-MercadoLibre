package domains

import(
	"../utils"
)

type Result struct {
	User *User
	Country *Country
	Site *Site
	ApiError *utils.ApiError
} // voy a devolver un usuario, el country que pertenece al usuario yel site que pertenece al country

