package woowacourse.movie.movie

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.movie.activity.SeatSelectionActivity
import woowacourse.movie.movie.activity.TicketActivity
import woowacourse.movie.movie.dto.movie.BookingMovieDto

class PendingIntentBuilder(val context: Context) {
    fun createNotificationPendingIntent(
        bookingMovie: BookingMovieDto,
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

    fun createReceiverPendingIntent(intent: Intent, bookingMovie: BookingMovieDto): PendingIntent {
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
