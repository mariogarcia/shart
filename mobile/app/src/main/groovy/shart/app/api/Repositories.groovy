package shart.app.api

import android.app.Activity
import retrofit.RestAdapter
import shart.app.R
import shart.app.cart.service.CartApiService

class Repositories {

    Activity activity

    private Repositories(Activity from) {
        this.activity = from
    }

    static Repositories from(Activity from) {
        return new Repositories(from)
    }

    CartApiService getCarts() {
        return new RestAdapter.Builder()
            .setEndpoint(activity.getString(R.string.host))
            .build()
            .create(CartApiService)
    }

}