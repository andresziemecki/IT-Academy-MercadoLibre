package services

import (
	"../utils"
	"../domains"
	"sync"
)

// Get Result Normal
func GetResult(userId int) (*domains.Result, *utils.ApiError) {
	user := domains.User{
		ID: userId,
	}

	if err := user.Get(); err != nil {
		return nil, err
	}

	country := domains.Country{
		ID: user.CountryID,
	}

	if err := country.Get(); err != nil {
		return nil, err
	}

	site := domains.Site{
		ID: user.SiteID,
	}

	if err := site.Get(); err != nil {
		return nil, err
	}

	resp := domains.Result{
		User:    &user,
		Site:    &site,
		Country: &country,
	}

	/*
		resp := &domains.Result{
		User: user,
		Site: site,
		Country: country,
	}
	*/

	return &resp, nil

}


// Get Result con WaitGroup
func GetResultWaitGroup(userId int) (*domains.Result, *utils.ApiError) {
	user := domains.User{
		ID: userId,
	}

	if err := user.Get(); err != nil {
		return nil, err
	}

	country := domains.Country{
		ID: user.CountryID,
	}

	site := domains.Site{
		ID: user.SiteID,
	}

	var wg sync.WaitGroup
	wg.Add(2)
	go country.GetWaitGroup(&wg)
	go site.GetWaitGroup(&wg)
	wg.Wait()

	resp := domains.Result{
		User:    &user,
		Site:    &site,
		Country: &country,
	}

	return &resp, nil

}

// GetResult con Channels
func GetResultChannel(userId int) (*domains.Result, *utils.ApiError) {
	user := domains.User{
		ID: userId,
	}

	if err := user.Get(); err != nil {
		return nil, err
	}

	country := domains.Country{
		ID: user.CountryID,
	}

	site := domains.Site{
		ID: user.SiteID,
	}

	canales := make(chan domains.Result, 2)

	resp := domains.Result{}

	go country.GetChannels(canales)
	go site.GetChannels(canales)


	for i:=0 ; i<2; i++ {

		canal := <- canales
		if canal.ApiError != nil {
			return nil, canal.ApiError
		}

		if canal.Country != nil {
			resp.Country = canal.Country
		}

		if canal.Site != nil {
			resp.Site = canal.Site
		}
	}

	resp.User = &user

	return &resp, nil

}