package com.mercadolibre.itacademy

import grails.rest.Resource

@Resource(uri='/marcas')
class Marca {

    int number
    String name
    static hasMany = [articulos:Articulo]

    static constraints = {
    }
}
