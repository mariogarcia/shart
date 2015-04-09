package shart.app.cart

import android.app.AlertDialog
import android.app.ListActivity
import android.os.Bundle
import android.view.MenuItem
import com.arasthel.swissknife.annotations.OnUIThread
import groovy.transform.CompileStatic
import shart.app.R
import shart.app.ast.OptionsMenu

@CompileStatic
@OptionsMenu(R.menu.menu_cart)
class CartActivity extends ListActivity {

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_list)
    }

    @Override
    boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_about) {
            showAbout()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @OnUIThread
    void showAbout() {
        new AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_launcher)
            .setTitle(R.string.app_name)
            .setView(layoutInflater.inflate(R.layout.about, null, false))
            .create()
            .show()
    }

}