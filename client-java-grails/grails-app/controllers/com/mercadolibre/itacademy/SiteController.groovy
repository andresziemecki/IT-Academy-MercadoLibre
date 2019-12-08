package com.mercadolibre.itacademy

import grails.converters.JSON
import groovy.json.JsonSlurper

class SiteController {

    // Cuando el controlador es llamado, depende que se llame retornara lo siguiente
    def index() {
        def url = new URL("http://localhost:8081/marcas") // Te puede devolver una jar connection o una http
        def connection = (HttpURLConnection) url.openConnection() // la teniamos que castear xq podia ser una jar connection
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        JsonSlurper json = new JsonSlurper()
        def sites = json.parse(connection.getInputStream()) // ahora esto va  aser un array de objetos json
        // ahora lo devuelvo en un mapa
        [sites:sites]
    }

    def categorias(String id) {
        def url = new URL("http://localhost:8081/marcas/"+id)
        // def url = new URL("https://api.mercadolibre.com/sites/"+id+"/categories") // Te puede devolver una jar connection o una http
        def connection = (HttpURLConnection) url.openConnection() // la teniamos que castear xq podia ser una jar connection
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        JsonSlurper json = new JsonSlurper()
        def categories = json.parse(connection.getInputStream())
        def cat = [categories:categories]
        render cat as JSON
    }

    def children(String id) {
        def url = new URL("http://localhost:8081/articulos/"+id)
        //def url = new URL("https://api.mercadolibre.com/categories/"+id)
        def connection = (HttpURLConnection) url.openConnection()
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        JsonSlurper json = new JsonSlurper()
        def childrens = json.parse(connection.getInputStream())
        def child = [childrens:childrens]
        render child as JSON
    }

    def deleteElement(String articuloId) {
        def url = new URL("http://localhost:8081/articulos/"+articuloId)
        def connection = (HttpURLConnection) url.openConnection()
        connection.setRequestMethod("DELETE")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        connection.getInputStream()
        def respuesta = [statuscode: 204]
        render respuesta as JSON
    }

    def createElement(String articuloId, String data) {
        def url = new URL("http://localhost:8081/articulos/"+articuloId)
        def connection = (HttpURLConnection) url.openConnection()
        connection.setRequestMethod("PUT")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        connection.setDoOutput(true)
        connection.setDoInput(true)
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter(connection, getOutputStream())
        outputstreamwriter.write(data)
        outputstreamwriter.flush()
        def JsonSlurper json = new JsonSlurper()
        def items = json.parse(connection.getInputStream())
        render items as JSON
    }


}
