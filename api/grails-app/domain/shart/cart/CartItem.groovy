package shart.cart

import shart.item.Item

class CartItem {

    static belongsTo = [cart: Cart]

    Cart cart
    Item item

    Integer howMany = 0
    Measure measure = Measure.UNIT

}
