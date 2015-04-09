package shart.app.cart.service

import groovy.transform.CompileStatic
import retrofit.client.Response
import retrofit.http.Body
import retrofit.http.Headers
import retrofit.http.POST
import shart.app.cart.Cart

@CompileStatic
interface CartApiService {

    @POST('/carts')
    @Headers('Content-Type: application/json')
    Cart createNewCart(@Body Cart cart)

}