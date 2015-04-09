package shart.cart

import shart.test.BaseSpec
import grails.transaction.Rollback
import grails.test.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired

@Rollback
@Integration
class CartServiceSpec extends BaseSpec {

    @Autowired CartService cartService

    void 'create a new cart successfully'() {
        when: 'trying to create a cart with a valid date'
            Cart newCart = cartService.createNewCart(new Date())
        then: 'we should be getting a new valid cart'
            newCart
    }

    void 'failing to create a new cart'() {
        when: 'trying to create a new cart without a date'
            Cart newCart = cartService.createNewCart(null)
        then: 'there will be no cart saved'
            !newCart
    }

    void 'delete a cart successfully'() {
        given: 'creating a new cart'
            Cart cart = DAP.cart.createNewCart()
        when: 'trying to delete the cart'
            Cart deleted = cartService.deleteCart(cart.id)
        then: 'we should get the deleted cart as result'
            deleted
        and: 'we shouldnt be able to find it anymore'
            !cartService.getCart(cart.id)
    }

}
