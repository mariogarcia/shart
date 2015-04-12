package shart.app.cart

import android.app.ListActivity
import android.view.View
import com.arasthel.swissknife.annotations.OnBackground
import com.arasthel.swissknife.annotations.OnClick
import com.arasthel.swissknife.annotations.OnUIThread
import groovy.transform.CompileStatic
import shart.app.R
import shart.app.ast.ContentView
import shart.app.ast.OnOptionsItemSelected
import shart.app.ast.OptionsMenu

import static shart.app.api.Repositories.from
import static shart.app.util.DateUtils.toRequest

@CompileStatic
@OptionsMenu(R.menu.menu_cart)
@ContentView(R.layout.cart_list)
class CartActivity extends ListActivity {

    static final String PAYLOAD = 'cart'

    @OnBackground
    @OnClick(R.id.button_add_new_cart)
    void createNewCart(View view) {
        Try(this.&saveCart) || Try { showMessage(R.string.error_getting_data) }
    }

    void saveCart() {
        Cart cart = from(this).carts.createNewCart(new Cart(cartDate: toRequest(new Date())))

        showMessage(R.string.cart_saved)
        startActivityWithExtra(CartItemListActivity, PAYLOAD, cart)
    }

    @OnUIThread
    @OnOptionsItemSelected(R.id.action_about)
    void showAbout() {
        showDialogWithLayout(R.layout.about) {
            icon  = R.drawable.ic_launcher
            title = getString(R.string.app_name)
        }
    }

}