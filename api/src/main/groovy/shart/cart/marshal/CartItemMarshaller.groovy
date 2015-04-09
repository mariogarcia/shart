package shart.cart.marshal

import shart.cart.CartItem
import shart.marshal.Marshaller
import org.springframework.stereotype.Component

@Component
class CartItemMarshaller extends Marshaller<CartItem> {

    CartItemMarshaller() {
        super(CartItem)
    }

    Map<?,?> marshal(CartItem cartItem) {
        return [
            'id': cartItem.id,
            'description': cartItem.item.description,
            'howMany': cartItem.howMany,
            'measure': cartItem.measure.toString()
        ]
    }

}
