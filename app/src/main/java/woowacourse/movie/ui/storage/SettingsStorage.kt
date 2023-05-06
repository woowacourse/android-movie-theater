package woowacourse.movie.ui.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SettingsStorage : PushNotificationRepository {
    private lateinit var sharedPref: SharedPreferences
    private const val KEY_PUSH_NOTIFICATION = "push_notification"

    fun init(context: Context) {
        sharedPref = context.getSharedPreferences("settings", MODE_PRIVATE)
    }

    override fun getPushNotification(): Boolean =
        sharedPref.getBoolean(KEY_PUSH_NOTIFICATION, false)

    override fun editPushNotification(isGranted: Boolean) {
        sharedPref.edit().putBoolean(KEY_PUSH_NOTIFICATION, isGranted).apply()
    }
}
