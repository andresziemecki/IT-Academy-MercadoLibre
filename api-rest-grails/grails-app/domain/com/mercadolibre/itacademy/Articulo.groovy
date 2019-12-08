package com.mercadolibre.itacademy

import grails.rest.Resource

@Resource(uri='/articulos')
class Articulo {

    int id
    String name
    String picture

    static belongsTo = [marca:Marca]

    static constraints = {
    }
}
