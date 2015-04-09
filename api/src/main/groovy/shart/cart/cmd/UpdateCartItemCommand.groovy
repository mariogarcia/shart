package shart.cart.cmd

import shart.cart.Measure
import grails.validation.Validateable

class UpdateCartItemCommand implements Validateable {

    Long itemId
    Integer howMany
    Measure measure

    Map<?,?> asMap() {
        return [
            itemId: itemId,
            howMany: howMany,
            measure: measure
        ]
    }

    static constraints = {
        itemId nullable: true
        howMany nullable: true
        measure nullable: true
    }

}
