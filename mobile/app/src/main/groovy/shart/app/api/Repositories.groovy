package shart.app.api

import android.app.Activity
import retrofit.RestAdapter
import shart.app.R
import shart.app.cart.service.CartApiService

class Repositories {

    static CartApiService getCartService(Activity activity) {
        return new RestAdapter.Builder()
            .setEndpoint(activity.getString(R.string.host))
            .build()
            .create(CartApiService)
    }

}