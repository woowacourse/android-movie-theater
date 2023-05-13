package woowacourse.movie.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.seat.SeatSelectionActivity
import woowacourse.movie.setting.AlarmReceiver
import woowacourse.movie.ticket.TicketActivity

class PendingIntentBuilder(val context: Context) {
    fun createNotificationPendingIntent(
        bookingMovie: BookingMovieEntity,
    ): PendingIntent {
        val ticketIntent =
            Intent(context, TicketActivity::class.java).putExtra(SeatSelectionActivity.BOOKING_MOVIE_KEY, bookingMovie)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

        return PendingIntent.getActivity(
            context,
            AlarmReceiver.REQUEST_CODE,
            ticketIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    fun createReceiverPendingIntent(intent: Intent, bookingMovie: BookingMovieEntity): PendingIntent {
        return intent.let {
            it.action = AlarmReceiver.ALARM_CODE
            it.putExtra(SeatSelectionActivity.BOOKING_MOVIE_KEY, bookingMovie)
            PendingIntent.getBroadcast(
                context,
                AlarmReceiver.REQUEST_CODE,
                it,
                PendingIntent.FLAG_IMMUTABLE,
            )
        }
    }
}
