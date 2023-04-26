package woowacourse.movie.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.ticketingresult.TicketingResultActivity
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.util.NotificationUtil

class PushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION) {
            intent.getParcelableCompat<Reservation>(TicketingResultActivity.RESERVATION_KEY)?.run {
                NotificationUtil.sendNotification(
                    context,
                    this,
                    context.getString(R.string.notification_push_title),
                    context.getString(R.string.notification_push_desc, movieTitle, 30)
                )
            }
        }
    }

    companion object {
        internal const val ACTION = "woowacourse.movie.presentation.receiver.AlarmReceiver"
    }
}
