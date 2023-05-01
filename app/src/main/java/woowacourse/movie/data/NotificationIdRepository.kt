package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences

object NotificationIdRepository {
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(
            "notification_atomic_id_prefs",
            Context.MODE_PRIVATE
        )
    }

    val notificationId: Int
        get() {
            val lastNotificationId = prefs.getInt(NOTIFICATION_ID, 0)
            val nextNotificationId = lastNotificationId + 1
            prefs.edit().putInt(NOTIFICATION_ID, nextNotificationId).apply()
            return nextNotificationId
        }

    private const val NOTIFICATION_ID = "notification_id"
}
