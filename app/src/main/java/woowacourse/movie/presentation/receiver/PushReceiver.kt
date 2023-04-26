package woowacourse.movie.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.ticketingresult.TicketingResultActivity
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.reminder.ReservationReminder

class PushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == PUSH_ACTION) {
            val reservation: Reservation =
                intent.getParcelableCompat(TicketingResultActivity.RESERVATION_KEY) ?: return

            val reservationReminder = ReservationReminder(
                channelId = context.getString(R.string.reservation_reminder_channel_id),
                channelName = context.getString(R.string.reservation_reminder_channel_name),
                channelDesc = context.getString(R.string.reservation_reminder_channel_desc),
            )
            reservationReminder.notify(context, reservation)
        }
    }

    companion object {
        internal const val PUSH_ACTION: String =
            "woowacourse.movie.presentation.receiver.PushReceiver"
    }
}
