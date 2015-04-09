package shart.app.util

import android.app.Activity
import android.content.Intent

final class ActivityUtils {

    static void startActivityWithSerializable(Activity from, Class<? extends Activity> to, String key, Serializable serializable) {
        Intent intent = new Intent(from, to)
        intent.putExtra(key, serializable)

        from.startActivity(intent)
    }

    static <U extends Serializable> U getExtraSerializable(Activity from, Class<U> type, String key) {
        return (U) from.intent.getSerializableExtra(key)
    }

    static Boolean Try(Closure<Void> action) {
        try {
            action()
            return true
        } catch (e) {
            return false
        }
    }

}