package shart.app.cart

import android.app.AlertDialog
import android.app.ListActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnBackground
import com.arasthel.swissknife.annotations.OnClick
import com.arasthel.swissknife.annotations.OnUIThread
import groovy.transform.CompileStatic
import shart.app.R
import shart.app.ast.OnOptionsItemSelected
import shart.app.ast.OptionsMenu

import static shart.app.api.Repositories.from
import static shart.app.util.ActivityUtils.Try
import static shart.app.util.ActivityUtils.startActivityWithSerializable
import static shart.app.util.DateUtils.toRequest

@CompileStatic
@OptionsMenu(R.menu.menu_cart)
class CartActivity extends ListActivity {

    static final String PAYLOAD = 'cart'

    @InjectView(R.id.button_add_new_cart)
    Button createNewCartButton

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_list)
        SwissKnife.inject(this)
    }

    @OnBackground
    @OnClick(R.id.button_add_new_cart)
    void createNewCart(View view) {
        Try(this.&saveCart) ||
        Try { showMessage(R.string.error_getting_data) }
     // Try { this } ||
     // Try { that } ||
    }

    void saveCart() {
        Cart cart = from(this).carts.createNewCart(new Cart(cartDate: toRequest(new Date())))
        showMessage(R.string.cart_saved)

        startActivityWithSerializable(this, CartItemListActivity, PAYLOAD, cart)
    }

    @OnUIThread
    void showMessage(int messageId) {
        Toast.makeText(baseContext, messageId, 6).show()
    }

    @OnUIThread
    @OnOptionsItemSelected(R.id.action_about)
    void showAbout() {
        new AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_launcher)
            .setTitle(R.string.app_name)
            .setView(layoutInflater.inflate(R.layout.about, null, false))
            .create()
            .show()
    }

}