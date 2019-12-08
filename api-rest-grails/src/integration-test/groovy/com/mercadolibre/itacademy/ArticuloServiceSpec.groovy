package com.mercadolibre.itacademy

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ArticuloServiceSpec extends Specification {

    ArticuloService articuloService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Articulo(...).save(flush: true, failOnError: true)
        //new Articulo(...).save(flush: true, failOnError: true)
        //Articulo articulo = new Articulo(...).save(flush: true, failOnError: true)
        //new Articulo(...).save(flush: true, failOnError: true)
        //new Articulo(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //articulo.id
    }

    void "test get"() {
        setupData()

        expect:
        articuloService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Articulo> articuloList = articuloService.list(max: 2, offset: 2)

        then:
        articuloList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        articuloService.count() == 5
    }

    void "test delete"() {
        Long articuloId = setupData()

        expect:
        articuloService.count() == 5

        when:
        articuloService.delete(articuloId)
        sessionFactory.currentSession.flush()

        then:
        articuloService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Articulo articulo = new Articulo()
        articuloService.save(articulo)

        then:
        articulo.id != null
    }
}
