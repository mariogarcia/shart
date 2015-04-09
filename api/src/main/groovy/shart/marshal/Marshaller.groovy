package shart.marshal

import grails.converters.XML
import grails.converters.JSON

abstract class Marshaller<T> {

    Class<T> clazzToRegister

    Marshaller(Class<T> clazz) {
        this.clazzToRegister = clazz
    }

    void register() {
        [XML, JSON]*.registerObjectMarshaller(clazzToRegister, this.&marshal)
    }

    abstract Map<?,?> marshal(T value)

}
