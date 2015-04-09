package shart.app.cart

import android.app.ListActivity
import android.os.Bundle
import android.widget.TextView
import com.arasthel.swissknife.annotations.OnBackground
import com.arasthel.swissknife.annotations.OnUIThread
import groovy.transform.CompileStatic
import shart.app.R

import static shart.app.util.DateUtils.fromResponse

@CompileStatic
class CartItemListActivity extends ListActivity {
    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)

        Cart cart = (Cart) intent.getSerializableExtra('cart')

        loadCartInfo(cart)
        setContentView(R.layout.cart_item_list)
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