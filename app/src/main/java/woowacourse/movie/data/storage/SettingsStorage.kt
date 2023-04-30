package woowacourse.movie.data.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SettingsStorage {
    private lateinit var sharedPref: SharedPreferences
    private val KEY_PUSH_NOTIFICATION = "push_notification"
    var enablePushNotification: Boolean
        get() = sharedPref.getBoolean(KEY_PUSH_NOTIFICATION, false)
        set(enable) {
            sharedPref
                .edit()
                .putBoolean(KEY_PUSH_NOTIFICATION, enable)
                .apply()
        }

    fun init(context: Context) {
        sharedPref = context.getSharedPreferences("settings", MODE_PRIVATE)
    }
}
