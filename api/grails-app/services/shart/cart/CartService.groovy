package shart.cart

import shart.util.Pagination

class CartService {

    CartRepository cartRepository

    Cart createNewCart(Date date) {
        return cartRepository.createCart(date)
    }

    List<Cart> list(Pagination pagination) {
        return cartRepository.list(pagination)
    }

    Cart deleteCart(Long cartId) {
        return cartRepository.deleteCart(cartId)
    }

    Cart getCart(Long cartId) {
        return cartRepository.getCart(cartId)
    }

    List<CartItem> listCartItems(Long cartId, Pagination pagination) {
        return cartRepository.listCartItems(cartId, pagination)
    }

    CartItem addCartItem(Long cartId, Long itemId) {
        return cartRepository.addCartItem(cartId, itemId)
    }

    CartItem updateCartItem(Long cartId, Long cartItemId, Map<?,?> data) {
        //cartRepository.checkCartItemBelongsToCart(cartItemId, cartId)

        return cartRepository.updateCartItem(cartItemId, data)
    }

    CartItem deleteCartItem(Long cartId, Long cartItemId) {
        //cartRepository.checkCartItemBelongsToCart(cartItemId, cartId)

        return cartRepository.deleteCartItem(cartItemId)
    }

}
