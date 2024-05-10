package woowacourse.movie.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPreference(context: Context) {
    private val sharedPref = context.getSharedPreferences("setting", MODE_PRIVATE)

    fun saveNotificationState(state: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(SHARED_PREF_KEY_NOTIFICATION, state)
            apply()
        }
    }

    fun isPushNotificationActivated(): Boolean = sharedPref.getBoolean(SHARED_PREF_KEY_NOTIFICATION, false)

    companion object {
        const val SHARED_PREF_KEY_NOTIFICATION = "notification"
    }
}
