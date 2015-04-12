package shart.app.ext

import android.app.Activity
import android.app.AlertDialog
import groovy.transform.CompileStatic

@CompileStatic
class Widgets {

    static void showDialogWithLayout(Activity shelf, int layout, @DelegatesTo(AlertDialog.Builder) Closure<?> settings) {
        AlertDialog.Builder builder =
            new AlertDialog.Builder(shelf).setView(shelf.layoutInflater.inflate(layout, null, false))

        builder.with(settings)
        builder.create().show()
    }
}