package shart.dap.cart

import shart.cart.Cart

class CartDataProvider {

    Cart createNewCart() {
        return new Cart(cartDate: new Date()).save()
    }

}
