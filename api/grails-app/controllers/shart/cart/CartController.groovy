package shart.cart

import shart.controller.BaseController
import shart.cart.cmd.CreateCartCommand
import shart.cart.cmd.AddItemToCartCommand
import shart.cart.cmd.UpdateCartItemCommand

class CartController extends BaseController {

    static responseFormats = ['json', 'xml']

    CartService cartService

    def list() {
        respond cartService.list(pagination)
    }

    def create(CreateCartCommand cmd) {
        respond cartService.createNewCart(cmd.cartDate)
    }

    def delete(Long cartId) {
        respond cartService.deleteCart(cartId)
    }

    def read(Long cartId) {
        respond cartService.getCart(cartId)
    }

    def itemList(Long cartId) {
        respond cartService.listCartItems(cartId, pagination)
    }

    def itemAdd(Long cartId, AddItemToCartCommand cmd) {
        respond cartService.addCartItem(cartId, cmd.itemId)
    }

    def itemUpdate(Long cartId, Long cartItemId, UpdateCartItemCommand cmd) {
        respond cartService.updateCartItem(cartId, cartItemId, cmd.asMap())
    }

    def itemDelete(Long cartId, Long cartItemId) {
        respond cartService.deleteCartItem(cartId, cartItemId)
    }

}
