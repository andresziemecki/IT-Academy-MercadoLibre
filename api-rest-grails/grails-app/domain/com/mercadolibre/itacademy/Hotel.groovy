package com.mercadolibre.itacademy

import grails.rest.Resource


@Resource(uri='/hotels')
class Hotel {


    String name
    static hasMany = [rooms:Room]

    static constraints = {
    name blank: false, nullable: false
    }
}
