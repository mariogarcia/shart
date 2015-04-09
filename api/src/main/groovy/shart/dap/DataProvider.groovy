package shart.dap

import shart.dap.cart.CartDataProvider

class DataProvider {

    CartDataProvider getCart() {
        return new CartDataProvider()
    }

}
