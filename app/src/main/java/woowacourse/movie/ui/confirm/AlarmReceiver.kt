package woowacourse.movie.ui.confirm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.PreferenceUtil
import woowacourse.movie.R
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.fragment.setting.SettingFragment.Companion.NOTIFICATIONS
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.util.sendNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val tickets = intent?.extras?.getParcelableCompat<TicketsState>(KEY_TICKETS) ?: return
        val sharedPreference = PreferenceUtil(context)
        val isNotification = sharedPreference.getBoolean(NOTIFICATIONS, false)

        if (isNotification) {
            context.sendNotification(
                R.drawable.ic_launcher_foreground,
                context.getString(R.string.alarm_receiver_notification_title),
                context.getString(R.string.alarm_receiver_notification_text)
                    .format(tickets.movieState.title),
                true
            )
        }
    }

    companion object {
        private const val KEY_TICKETS = "key_tickets"

        fun getIntent(context: Context, tickets: TicketsState): Intent = Intent().apply {
            setClass(context, AlarmReceiver::class.java)
            putExtra(KEY_TICKETS, tickets)
        }
    }
}
