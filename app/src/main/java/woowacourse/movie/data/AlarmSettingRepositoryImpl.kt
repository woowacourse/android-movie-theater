package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences
import com.example.domain.repository.AlarmSettingRepository

object AlarmSettingRepositoryImpl : AlarmSettingRepository {
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(
            "notifications_prefs",
            Context.MODE_PRIVATE
        )
    }

    override fun getEnablePushNotification(): Boolean = prefs.getBoolean(NOTIFICATIONS, false)

    override fun setEnablePushNotification(value: Boolean) {
        prefs.edit().putBoolean(NOTIFICATIONS, value).apply()
    }

    private const val NOTIFICATIONS = "notifications"
}
