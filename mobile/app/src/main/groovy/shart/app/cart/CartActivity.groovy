package shart.app.cart

import android.app.Activity
import android.app.AlertDialog
import android.app.ListActivity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.arasthel.swissknife.annotations.OnUIThread
import groovy.transform.CompileStatic
import shart.app.R
import shart.app.api.Repositories
import shart.app.ast.OnOptionsItemSelected
import shart.app.ast.OptionsMenu

import static shart.app.util.DateUtils.*

@CompileStatic
@OptionsMenu(R.menu.menu_cart)
class CartActivity extends ListActivity {

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_list)
        final Activity thisActivity = this

        Button button = (Button) findViewById(R.id.button_add_new_cart)
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            void onClick(View v) {
                new AsyncTask<Object,Integer,Cart>() {
                    @Override
                    protected Cart doInBackground(Object[] params) {
                         return Repositories.getCartService(thisActivity).createNewCart(new Cart(cartDate: toRequest(new Date())))
                    }
                    @Override
                    protected void onPostExecute(Cart cart) {
                        Intent intent = new Intent(thisActivity, CartItemListActivity)
                        intent.putExtra('cart', cart)
                        thisActivity.startActivity(intent)
                    }
                }.execute()
            }
        })
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