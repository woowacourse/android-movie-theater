package woowacourse.movie.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.seat.view.SeatSelectionActivity
import woowacourse.movie.setting.AlarmReceiver
import woowacourse.movie.ticket.model.BookingMovieModel
import woowacourse.movie.ticket.view.TicketActivity

class PendingIntentBuilder(val context: Context) {
    fun createNotificationPendingIntent(
        bookingMovie: BookingMovieModel,
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

    fun createReceiverPendingIntent(intent: Intent, bookingMovie: BookingMovieModel): PendingIntent {
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
