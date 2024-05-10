package woowacourse.movie.data.datastore

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class DefaultNotificationDataStore private constructor(
    private val preferences: SharedPreferences
) : NotificationDataStore {

    override var canNotification: Boolean
        get() = preferences.getBoolean(NOTIFICATION_KEY, false)
        set(value) {
            preferences.edit(commit = true) {
                putBoolean(NOTIFICATION_KEY, value)
            }
        }

    companion object {
        @Volatile
        private var INSTANCE: NotificationDataStore? = null
        private const val DATASTORE_KEY = "DATASTORE_KEY"
        private const val NOTIFICATION_KEY = "NOTIFICATION_KEY"

        fun instance(context: Context): NotificationDataStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DefaultNotificationDataStore(
                    context.getSharedPreferences(DATASTORE_KEY, Context.MODE_PRIVATE)
                ).also { INSTANCE = it }
            }
        }
    }
}