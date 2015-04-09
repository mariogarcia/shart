package shart.app.cart

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@Immutable
@CompileStatic
class Cart implements Serializable {
    Long id
    String cartDate
}