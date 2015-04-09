package shart.cart

import shart.item.Item
import shart.util.Pagination
import org.springframework.stereotype.Component

@Component
class CartRepository {

    Cart createCart(Date date) {
        return new Cart(cartDate: date).save()
    }

    List<Cart> list(Pagination pagination) {
        return Cart.list(max: pagination.max, offset: pagination.offset)
    }

    Cart deleteCart(Long cartId) {
        Cart cartToDelete = Cart.get(cartId)
        cartToDelete.delete()

        return cartToDelete
    }

    Cart getCart(Long cartId) {
        return Cart.get(cartId)
    }

    List<CartItem> listCartItems(Long cartId, Pagination pagination) {
        return CartItem
            .where { cart.id == cartId }
            .list(max: pagination.max, offset: pagination.offset)
    }

    CartItem addCartItem(Long cartId, Long itemId) {
        Cart cart = getCart(cartId)
        Item item = Item.get(itemId)

        return new CartItem(item: item, cart: cart).save()
    }

    void checkCartItemBelongsToCart(Long cartItemId, Long cartId) {
        CartItem cartItem = CartItem.get(cartItemId)
        Cart cart = getCart(cartId)

        if (!cartItem || !cart) throw new RuntimeException('Bad cart/cartItems')
        if (cartItem.cart.id != cart.id) throw new RuntimeException('Wrong cartId')
    }

    CartItem updateCartItem(Long cartItemId, Map<?,?> data) {
        CartItem cartItem = CartItem.get(cartItemId)
        Item item = Item.get(data.itemId)

        if (item) {
            cartItem.item = item
        }

        if (data.howMany) {
            cartItem.howMany = data.howMany
        }

        if (data.measure) {
            cartItem.measure = data.measure
        }

        return cartItem.save()
    }

    CartItem deleteCartItem(Long cartItemId) {
        CartItem cartItem = CartItem.get(cartItemId)
        cartItem.delete()

        return cartItem
    }

}
