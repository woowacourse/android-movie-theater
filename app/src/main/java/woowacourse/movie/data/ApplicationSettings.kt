package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object ApplicationSettings {
    private lateinit var settings: SharedPreferences

    fun init(applicationContext: Context) {
        settings = applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    var notificationEnabled: Boolean
        get() {
            return settings.getBoolean(KEY_NOTIFICATION_ENABLED, false)
        }
        set(value) {
            settings.edit {
                putBoolean(KEY_NOTIFICATION_ENABLED, value)
            }
        }

    private const val KEY_NOTIFICATION_ENABLED = "KEY_NOTIFICATION_ENABLED"
    private const val FILE_NAME = "settings"
}
