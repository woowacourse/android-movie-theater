package woowacourse.movie.ui.confirm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.DefaultPreference
import woowacourse.movie.R
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.main.setting.SettingFragment.Companion.NOTIFICATIONS
import woowacourse.movie.util.NotificationArgs
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.util.sendNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val tickets = intent?.extras?.getParcelableCompat<TicketsState>(KEY_TICKETS) ?: return
        val sharedPreference = DefaultPreference(context)
        val isNotification = sharedPreference.getBoolean(NOTIFICATIONS, false)

        if (isNotification) {
            sendNotification(context, tickets)
        }
    }

    private fun sendNotification(context: Context, tickets: TicketsState) {
        val notificationArgs = NotificationArgs(
            iconResId = R.drawable.ic_launcher_foreground,
            contentTitle = context.getString(R.string.alarm_receiver_notification_title),
            contentText = context.getString(R.string.alarm_receiver_notification_text).format(
                tickets.movieState.title
            ),
            cancelable = true
        )
        context.sendNotification(notificationArgs)
    }

    companion object {
        private const val KEY_TICKETS = "key_tickets"

        fun getIntent(context: Context, tickets: TicketsState): Intent = Intent().apply {
            setClass(context, AlarmReceiver::class.java)
            putExtra(KEY_TICKETS, tickets)
        }
    }
}
