package woowacourse.movie.data.notification

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class NotificationPreferenceImpl private constructor(context: Context) : NotificationPreference {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun isNotificationEnabled(): Boolean {
        return prefs.getBoolean(KEY_NOTIFICATION_ENABLED, true)
    }

    override fun setNotificationEnabled(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_NOTIFICATION_ENABLED, enabled) }
    }

    companion object {
        private const val PREF_NAME = "notification_setting"
        private const val KEY_NOTIFICATION_ENABLED = "notification_enabled"

        private var _INSTANCE: NotificationPreferenceImpl? = null
        val INSTANCE: NotificationPreferenceImpl
            get() = _INSTANCE ?: throw IllegalArgumentException("[ERROR] SharedPreference가 초기화되지 않았습니다.")

        fun initialize(context: Context) {
            if (_INSTANCE == null) {
                _INSTANCE = NotificationPreferenceImpl(context)
            }
        }
    }
}
