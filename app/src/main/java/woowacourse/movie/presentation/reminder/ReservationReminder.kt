package woowacourse.movie.presentation.reminder

import android.app.PendingIntent
import android.content.Context
import android.os.Parcelable
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.ticketing.SeatPickerActivity.Companion.REMINDER_TIME_MINUTES_AGO
import woowacourse.movie.presentation.activities.ticketingresult.TicketingResultActivity
import woowacourse.movie.presentation.model.Reservation

class ReservationReminder(
    override val channelId: String = "reservation_reminder",
    override val channelName: String = "default channel name",
    override val channelDesc: String = "default description",
) : Reminder() {

    override fun <T : Parcelable> notify(context: Context, data: T) {
        if (data !is Reservation) return

        with(context) {
            sendNotification(
                this,
                getString(R.string.notification_push_title),
                getString(
                    R.string.notification_push_desc,
                    data.movieTitle,
                    REMINDER_TIME_MINUTES_AGO
                ),
                RESERVATION_PUSH_ID,
            ) { makePendingIntent(context, data) }
        }
    }

    private fun makePendingIntent(
        context: Context,
        data: Reservation
    ): PendingIntent {
        val intent = TicketingResultActivity.intent(context, data)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    companion object {
        private const val RESERVATION_PUSH_ID = 1000
    }
}
