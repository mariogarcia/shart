package shart.item

import shart.util.Pagination
import shart.item.cmd.SearchItemCommand

class ItemService {

    ItemRepository itemRepository

    Item createNewItem(String description) {
        return itemRepository.createNewItem(description)
    }

    List<Item> list(SearchItemCommand command, Pagination pagination) {
        return itemRepository.list(command, pagination)
    }

}
