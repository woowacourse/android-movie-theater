package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(
            "notifications_prefs",
            Context.MODE_PRIVATE
        )

    var enablePushNotification: Boolean
        get() = prefs.getBoolean(NOTIFICATIONS, false)
        set(value) {
            prefs.edit().putBoolean(NOTIFICATIONS, value).apply()
        }

    companion object {
        private const val NOTIFICATIONS = "notifications"
    }
}
