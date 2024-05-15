package woowacourse.movie.database

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class NotificationSharedPreference(context: Context) {
    private val sharedPreference =
        context.getSharedPreferences(SHARED_PREFERENCE_SETTING, AppCompatActivity.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreference.edit()
    fun load(): Boolean {
        return sharedPreference.getBoolean(NOTIFICATION_KEY, false)
    }

    fun save(isNotificationGranted: Boolean) {
        editor.putBoolean(NOTIFICATION_KEY, isNotificationGranted).apply()
    }

    companion object {
        const val SHARED_PREFERENCE_SETTING = "settings"
        const val NOTIFICATION_KEY = "notification"
    }
}
