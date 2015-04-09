package shart.item.marshal

import shart.item.Item
import shart.marshal.Marshaller
import org.springframework.stereotype.Component

@Component
class ItemMarshaller extends Marshaller<Item> {

    ItemMarshaller() {
        super(Item)
    }

    Map<?,?> marshal(Item item) {
        return [
            'id': item.id,
            'description': item.description
        ]
    }

}
