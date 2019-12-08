package com.mercadolibre.itacademy

import grails.converters.JSON

import java.text.SimpleDateFormat

class BootStrap {

    def init = { servletContext ->
        def hotel1 = new Hotel(name: 'Hotel Vista').save(flush: true)
        def hotel2 = new Hotel(name: 'Premium Tower').save(flush: true)
        hotel1.addToRooms(new Room(number: 201)).save()
        hotel1.addToRooms(new Room(number: 202)).save()
        hotel1.addToRooms(new Room(number: 203)).save()
        hotel2.addToRooms(new Room(number: 301)).save()
        hotel2.addToRooms(new Room(number: 302)).save()
        hotel2.addToRooms(new Room(number: 303)).save()

        def marcaApple = new Marca(name: 'Apple').save(flush:true)
        def marcaSamsung = new Marca(name: 'Samsung').save(flush:true)

        marcaApple.addToArticulos(new Articulo(name: 'iPhone6', picture: 'https://www.ishopperu.com.pe/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/i/p/iphone-6s-space-grey.jpg')).save()
        marcaApple.addToArticulos(new Articulo (name: 'iPhoneXr', picture: 'https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/iphone-xr-red-select-201809?wid=940&hei=1112&fmt=png-alpha&qlt=80&.v=1551226038669')).save()

        marcaSamsung.addToArticulos(new Articulo(name: 'Samsung S7', picture: 'https://home.ripley.cl/store/Attachment/WOP/D191/2000372348531/2000372348531-1.jpg')).save()
        marcaSamsung.addToArticulos(new Articulo(name: 'Samsung S8', picture: 'https://images-na.ssl-images-amazon.com/images/I/61mMo3xweeL._SX569_.jpg')).save()

        marshaler()
    }
    def destroy = {
    }

    private void marshaler() { // Esto le va a cambiar la estructura, le puedo poner la estructura json que yo quiera
        JSON.registerObjectMarshaller(Hotel) {
                // Un hotel se representa de esta forma
            hotel ->
                [
                        id   : hotel.id,
                        name : hotel.name,
                        rooms: hotel.rooms.collect {
                            room ->
                                [
                                        id    : room.id,
                                        number: room.number
                                ]
                        }
                ]
        }

        JSON.registerObjectMarshaller(Room) {
                // Una habitacion se representa de esta forma
            room ->
                [
                        id    : room.id,
                        number: room.number,
                        date  : new SimpleDateFormat("dd/MM/yyyy").format(new Date()) // Esto no se graba en la base de datos
                ]
        }

        JSON.registerObjectMarshaller(Marca) {
            marca -> [
                    id: marca.id,
                    name: marca.name,
                    children_categories: marca.articulos.collect {
                        children_categories -> [
                                id: children_categories.id,
                                name: children_categories.name,
                                children_categories: []
                        ]
                    }
            ]
        }

        JSON.registerObjectMarshaller(Articulo){
            articulo -> [
                id: articulo.id,
                name: articulo.name,
                picture: articulo.picture,
                    children_categories: []
            ]
        }

    }

}

