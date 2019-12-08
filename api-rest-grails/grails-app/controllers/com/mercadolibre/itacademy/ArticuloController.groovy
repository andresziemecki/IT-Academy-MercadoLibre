package com.mercadolibre.itacademy

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.rest.RestfulController


class ArticuloController extends RestfulController<Articulo>{

    ArticuloService articuloService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static responseFormats = ['json']

    ArticuloController() {
        super(Articulo)
    }

    @Override
    def index() {
        def marcaId = params.marcaId
        if(marcaId != null) {
            respond Articulo.where {
                marca.id == marcaId
            }.list()
        } else {
            respond Articulo.list()
        }
    }
/*
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond articuloService.list(params), model:[articuloCount: articuloService.count()]
    }
*/
    def show(Long id) {
        respond articuloService.get(id)
    }

    def create() {
        respond new Articulo(params)
    }

    def save(Articulo articulo) {
        if (articulo == null) {
            notFound()
            return
        }

        try {
            articuloService.save(articulo)
        } catch (ValidationException e) {
            respond articulo.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'articulo.label', default: 'Articulo'), articulo.id])
                redirect articulo
            }
            '*' { respond articulo, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond articuloService.get(id)
    }

    def update(Articulo articulo) {
        if (articulo == null) {
            notFound()
            return
        }

        try {
            articuloService.save(articulo)
        } catch (ValidationException e) {
            respond articulo.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'articulo.label', default: 'Articulo'), articulo.id])
                redirect articulo
            }
            '*'{ respond articulo, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        articuloService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'articulo.label', default: 'Articulo'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'articulo.label', default: 'Articulo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
