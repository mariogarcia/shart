package shart.item.cmd

import grails.validation.Validateable

class SearchItemCommand implements Validateable {

    String description

    static constraints = {
        description size: (3..20)
    }

}
