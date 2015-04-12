package shart.app.cart

import android.app.ListActivity
import android.os.Bundle
import android.widget.TextView
import com.arasthel.swissknife.annotations.OnBackground
import com.arasthel.swissknife.annotations.OnUIThread
import groovy.transform.CompileStatic
import shart.app.R

import static shart.app.ext.Activities.getExtraSerializable

@CompileStatic
class CartItemListActivity extends ListActivity {
    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_item_list)

        Cart cart = getExtraSerializable(this, Cart, CartActivity.PAYLOAD)
        loadCartInfo(cart)
    }

    @OnBackground
    void loadCartInfo(Cart cart) {
        // Retrieve all cart items here!!
        paintCartInfo(cart)
    }

    @OnUIThread
    void paintCartInfo(Cart cart /*, List<CartItem> cartItemList */) {
        // Paint all retrieved info here
        TextView dateText = (TextView) findViewById(R.id.header_when)

        dateText.text = cart.cartDate
    }

}