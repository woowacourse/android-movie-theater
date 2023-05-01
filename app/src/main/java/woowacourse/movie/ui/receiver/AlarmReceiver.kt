package woowacourse.movie.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.fragment.setting.SettingFragment.Companion.NOTIFICATIONS
import woowacourse.movie.util.PreferenceUtil
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.util.notification.NotificationSettings
import woowacourse.movie.util.notification.sendNotification

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val tickets: TicketsState = intent?.extras?.getParcelableCompat(KEY_TICKETS) ?: return
        val sharedPreference = PreferenceUtil(context)
        val isNotification = sharedPreference.getBoolean(NOTIFICATIONS, false)

        if (isNotification) {
            val notificationSettings = NotificationSettings(
                contentTitle = context.getString(R.string.alarm_receiver_notification_title),
                contentText = context.getString(R.string.alarm_receiver_notification_text).format(
                    tickets.movieState.title
                ),
                cancelable = true
            )
            context.sendNotification(notificationSettings)
        }
    }

    companion object {
        const val KEY_TICKETS = "key_tickets"
    }
}
