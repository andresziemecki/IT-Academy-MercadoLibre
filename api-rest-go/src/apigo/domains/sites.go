package domains

import (
	"../utils"
	"net/http"
	"fmt"
	"io/ioutil"
	"encoding/json"
	"sync"
)

type Site struct {
	ID                 string   `json:"id"`
	Name               string   `json:"name"`
	CountryID          string   `json:"country_id"`
	SaleFeesMode       string   `json:"sale_fees_mode"`
	MercadopagoVersion int      `json:"mercadopago_version"`
	DefaultCurrencyID  string   `json:"default_currency_id"`
	ImmediatePayment   string   `json:"immediate_payment"`
	PaymentMethodIds   []string `json:"payment_method_ids"`
	Settings           struct {
		IdentificationTypes      []string `json:"identification_types"`
		TaxpayerTypes            []string `json:"taxpayer_types"`
		IdentificationTypesRules []struct {
			IdentificationType string `json:"identification_type"`
			Rules              []struct {
				EnabledTaxpayerTypes []interface{} `json:"enabled_taxpayer_types"`
				BeginsWith           string        `json:"begins_with"`
				Type                 string        `json:"type"`
				MinLength            int           `json:"min_length"`
				MaxLength            int           `json:"max_length"`
			} `json:"rules"`
		} `json:"identification_types_rules"`
	} `json:"settings"`
	Currencies []struct {
		ID     string `json:"id"`
		Symbol string `json:"symbol"`
	} `json:"currencies"`
	Categories []struct {
		ID   string `json:"id"`
		Name string `json:"name"`
	} `json:"categories"`
}

// Yo tengo que construir un reciver para que me devuelva un site armado
func (site *Site) Get() *utils.ApiError{
	if site.ID == "" {
		return &utils.ApiError{
			Message: "Site ID is empty",
			Status: http.StatusBadRequest,
		}
	}

	url := fmt.Sprintf("%s%s", utils.UrlSite, site.ID) //el SprintF permite formatear lo que hay en la url
	response, err := http.Get(url)
	if err != nil {
		return &utils.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}

	data, err := ioutil.ReadAll(response.Body)
	if err != nil {
		return &utils.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}

	if err := json.Unmarshal(data, &site); err != nil { // Esta variable es local en el if
		return &utils.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}

	return nil
}



func (site *Site) GetWaitGroup(wg *sync.WaitGroup) *utils.ApiError{
	if site.ID == "" {
		wg.Done()
		return &utils.ApiError{
			Message: "Site ID is empty",
			Status: http.StatusBadRequest,
		}
	}

	url := fmt.Sprintf("%s%s", utils.UrlSite, site.ID) //el SprintF permite formatear lo que hay en la url
	response, err := http.Get(url)
	if err != nil {
		wg.Done()
		return &utils.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}

	data, err := ioutil.ReadAll(response.Body)
	if err != nil {
		wg.Done()
		return &utils.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}

	if err := json.Unmarshal(data, &site); err != nil { // Esta variable es local en el if
		wg.Done()
		return &utils.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}
	wg.Done()
	return nil
}


func (site *Site) GetChannels(canales chan Result){

	if site.ID == "" {
		canales <- Result{
			ApiError: &utils.ApiError{
				Message: "Site ID is empty",
				Status:  http.StatusBadRequest,
			},
		}
	}

	url := fmt.Sprintf("%s%s", utils.UrlSite, site.ID) //el SprintF permite formatear lo que hay en la url

	response, err := http.Get(url)

	if err != nil {
		canales <- Result{
			ApiError: &utils.ApiError{
				Message: err.Error(),
				Status: http.StatusInternalServerError,
			},
		}
		return
	}

	data, err := ioutil.ReadAll(response.Body)

	if err != nil {
		canales <- Result{
			ApiError: &utils.ApiError{
				Message: err.Error(),
				Status: http.StatusInternalServerError,
			},
		}
		return
	}

	if err := json.Unmarshal(data, &site); err != nil { // Esta variable es local en el if
		canales <- Result{
			ApiError: &utils.ApiError{
				Message: err.Error(),
				Status: http.StatusInternalServerError,
			},
		}
		return
	}
	canales <- Result{
		Site:site,
	}
	return
}