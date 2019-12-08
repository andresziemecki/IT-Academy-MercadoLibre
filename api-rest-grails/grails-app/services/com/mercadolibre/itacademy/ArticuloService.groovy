package com.mercadolibre.itacademy

import grails.gorm.services.Service

@Service(Articulo)
interface ArticuloService {

    Articulo get(Serializable id)

    List<Articulo> list(Map args)

    Long count()

    void delete(Serializable id)

    Articulo save(Articulo articulo)

}