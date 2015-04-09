package shart.cart.marshal

import shart.cart.Cart
import shart.marshal.Marshaller
import org.springframework.stereotype.Component

@Component
class CartMarshaller extends Marshaller<Cart> {

    CartMarshaller() {
        super(Cart)
    }

    Map<?,?> marshal(Cart value) {
        return [
            'id': value.id,
            'cartDate': value.cartDate
        ]
    }

}
