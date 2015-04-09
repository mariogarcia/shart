package shart.item

import shart.controller.BaseController
import shart.item.cmd.CreateItemCommand
import shart.item.cmd.SearchItemCommand

class ItemController extends BaseController {

    static responseFormats = ['json', 'xml']

    ItemService itemService

    def create(CreateItemCommand cmd) {
        respond itemService.createNewItem(cmd.description)
    }

    def list(SearchItemCommand cmd) {
        respond itemService.list(cmd, pagination)
    }

}
