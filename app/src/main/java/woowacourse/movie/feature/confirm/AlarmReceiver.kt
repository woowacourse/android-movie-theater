package woowacourse.movie.feature.confirm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.data.AlarmSettingRepositoryImpl
import woowacourse.movie.model.TicketsState
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.util.sendNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val isNotification = AlarmSettingRepositoryImpl.enablePushNotification
        if (isNotification.not()) return

        val tickets = intent?.extras?.getParcelableCompat<TicketsState>(TICKETS)

        tickets?.let {
            context.sendNotification(
                R.drawable.ic_launcher_foreground,
                context.getString(R.string.alarm_receiver_notification_title),
                context.getString(R.string.alarm_receiver_notification_text)
                    .format(it.movieState.title),
                true
            )
        }
    }

    companion object {
        fun getIntent(context: Context, tickets: TicketsState): Intent {
            return Intent(context, AlarmReceiver::class.java).apply { putExtra(TICKETS, tickets) }
        }

        private const val TICKETS = "tickets"
    }
}
