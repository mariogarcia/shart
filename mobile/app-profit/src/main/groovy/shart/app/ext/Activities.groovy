package shart.app.ext

import android.app.Activity
import android.content.Intent
import com.arasthel.swissknife.annotations.OnUIThread
import com.arasthel.swissknife.dsl.AndroidDSL

final class Activities {

    static void startActivityWithExtra(Activity from, Class<? extends Activity> to, String key, Serializable serializable) {
        Intent intent = new Intent(from, to)
        intent.putExtra(key, serializable)

        from.startActivity(intent)
    }

    static <U extends Serializable> U getExtraSerializable(Activity from, Class<U> type, String key) {
        return (U) from.intent.getSerializableExtra(key)
    }

    static Boolean Try(Activity activity, Closure<Void> action) {
        try {
            action()
            return true
        } catch (e) {
            return false
        }
    }

    @OnUIThread
    static void showMessage(Activity activity, int message) {
        AndroidDSL.showToast(activity, activity.getString(message))
    }

}