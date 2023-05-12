package woowacourse.movie.presentation.notification

import android.app.PendingIntent
import android.content.Context
import android.os.Parcelable
import woowacourse.movie.R
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.ui.seatpicker.SeatPickerActivity.Companion.REMINDER_TIME_MINUTES_AGO
import woowacourse.movie.presentation.ui.ticketingresult.TicketingResultActivity

class ReservationNotification(
    override val channelId: String = "reservation_reminder",
    override val channelName: String = "default channel name",
    override val channelDesc: String = "default description",
) : Notifiable() {

    override fun <T : Parcelable> notify(context: Context, data: T) {
        if (data !is Reservation) return

        with(context) {
            sendNotification(
                context = this,
                title = getString(R.string.notification_push_title),
                content = getString(
                    R.string.notification_push_desc,
                    data.movieTitle,
                    REMINDER_TIME_MINUTES_AGO
                ),
                pushId = data.hashCode(),
            ) { makePendingIntent(context, data) }
        }
    }

    private fun makePendingIntent(
        context: Context,
        data: Reservation
    ): PendingIntent {
        val intent = TicketingResultActivity.getIntent(context, data)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
}
