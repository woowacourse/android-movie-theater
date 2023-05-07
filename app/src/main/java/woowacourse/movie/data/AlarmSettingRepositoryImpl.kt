package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences
import com.example.domain.repository.AlarmSettingRepository

class AlarmSettingRepositoryImpl(context: Context) : AlarmSettingRepository {
    private val prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(
            "notifications_prefs",
            Context.MODE_PRIVATE
        )
    }

    override fun getEnablePushNotification(): Boolean = prefs.getBoolean(NOTIFICATIONS, false)

    override fun setEnablePushNotification(value: Boolean) {
        prefs.edit().putBoolean(NOTIFICATIONS, value).apply()
    }

    companion object {
        private const val NOTIFICATIONS = "notifications"
    }
}
