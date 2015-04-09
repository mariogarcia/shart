package shart.item

import shart.util.Pagination
import shart.item.cmd.SearchItemCommand
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

@Slf4j
@Component
class ItemRepository {

    Item createNewItem(String description) {
        return new Item(description: description).save()
    }

    Item getItem(Long itemId) {
        return Item.get(itemId)
    }

    List<Item> list(SearchItemCommand command, Pagination pagination) {
        return Item
            .createCriteria()
            .list(max: pagination.max, offset: pagination.offset) {
                if (command.description) {
                    ilike('description', "${command.description}%")
                }
            }
    }

}
