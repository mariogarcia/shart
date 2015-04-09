package shart.controller

import shart.util.Pagination

class BaseController {

    Pagination getPagination() {
        return new Pagination(
            offset: params.offset ?: 0,
            max: params.max ?: 20
        )
    }

}
