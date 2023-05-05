package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences

object AlarmSettingRepositoryImpl : AlarmSettingRepository {
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(
            "notifications_prefs",
            Context.MODE_PRIVATE
        )
    }

    private const val NOTIFICATIONS = "notifications"
    override fun getEnablePushNotification(): Boolean = prefs.getBoolean(NOTIFICATIONS, false)

    override fun setEnablePushNotification(value: Boolean) {
        prefs.edit().putBoolean(NOTIFICATIONS, value).apply()
    }
}
