package woowacourse.movie.data.datastore

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.core.content.edit

class DefaultNotificationDataStore private constructor(
    private val preferences: SharedPreferences,
) : NotificationDataStore {
    override var acceptedPushAlarm: Boolean
        get() = preferences.getBoolean(NOTIFICATION_KEY, defaultCanNotification())
        set(value) {
            preferences.edit(commit = true) {
                putBoolean(NOTIFICATION_KEY, value)
            }
        }

    override var hasBeenDeniedPermission: Boolean
        get() = preferences.getBoolean(POST_NOTIFICATIONS, false)
        set(value) {
            preferences.edit(commit = true) {
                putBoolean(POST_NOTIFICATIONS, value)
            }
        }

    private fun defaultCanNotification(): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU

    companion object {
        @Volatile
        private var instance: NotificationDataStore? = null
        private const val DATASTORE_KEY = "DATASTORE_KEY"
        private const val NOTIFICATION_KEY = "NOTIFICATION_KEY"
        private const val POST_NOTIFICATIONS = "POST_NOTIFICATIONS"

        fun instance(context: Context): NotificationDataStore {
            return instance ?: synchronized(this) {
                instance ?: DefaultNotificationDataStore(
                    context.getSharedPreferences(DATASTORE_KEY, Context.MODE_PRIVATE),
                ).also { instance = it }
            }
        }
    }
}
