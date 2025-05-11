package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class ApplicationSettings(
    applicationContext: Context,
) {
    private val settings: SharedPreferences =
        applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    var notificationEnabled: Boolean
        get() {
            return settings.getBoolean(KEY_NOTIFICATION_ENABLED, false)
        }
        set(value) {
            settings.edit {
                putBoolean(KEY_NOTIFICATION_ENABLED, value)
            }
        }

    companion object {
        private const val KEY_NOTIFICATION_ENABLED = "KEY_NOTIFICATION_ENABLED"
    }
}
