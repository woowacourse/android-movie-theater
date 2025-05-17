package woowacourse.movie.providers

import android.content.SharedPreferences
import androidx.core.content.edit

object StorageProvider {
    const val PREFERENCE_KEY = "THEATER_PREFERENCE_KEY"
    private const val IS_FIRST_NOTIFICATION_REQUEST_KEY = "IS_FIRST_NOTIFICATION_REQUEST_KEY"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val isFirstPostNotificationPermissionRequest: Boolean
        get() = sharedPreferences.getBoolean(
            IS_FIRST_NOTIFICATION_REQUEST_KEY,
            true
        )

    fun setFirstPostNotificationPermissionRequestState(isFirst: Boolean) {
        sharedPreferences.edit {
            putBoolean(IS_FIRST_NOTIFICATION_REQUEST_KEY, isFirst)
        }
    }
}
