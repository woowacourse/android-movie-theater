package woowacourse.movie.broadcast.bookingnotificaiotn

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.domain.Reservation
import com.example.domain.ReservationRepository
import woowacourse.movie.model.ReservationResult
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.view.main.home.bookcomplete.BookCompleteActivity

class BookingAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        processReceiveEvent(intent, context)
    }

    private fun processReceiveEvent(
        intent: Intent?,
        context: Context?
    ) {
        val reservationResult =
            intent?.getParcelableCompat<ReservationResult>(RESERVATION_INTENT_KEY) ?: return
        saveReservation(reservationResult)

        val activityIntent = getActivityIntent(context, intent)
        val pendingIntent = getPendingIntent(context, activityIntent)

        sendNotification(context, pendingIntent, reservationResult)
    }

    private fun sendNotification(
        context: Context?,
        pendingIntent: PendingIntent,
        reservationResult: ReservationResult
    ) {
        val bookingNotification = BookingNotification(context!!, pendingIntent)
        bookingNotification.sendNotification(reservationResult.movieTitle)
    }

    private fun getPendingIntent(
        context: Context?,
        activityIntent: Intent
    ) = PendingIntent.getActivity(
        context!!, 0, activityIntent, FLAG_IMMUTABLE
    )

    private fun getActivityIntent(context: Context?, intent: Intent): Intent {
        return Intent(
            context, BookCompleteActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(
                BookCompleteActivity.RESERVATION_ID_INTENT_KEY,
                intent.getLongExtra(
                    BookCompleteActivity.RESERVATION_ID_INTENT_KEY,
                    -1L
                )
            )
        }
    }

    private fun saveReservation(reservationResult: ReservationResult) {
        ReservationRepository.findById(reservationResult.id) ?: ReservationRepository.save(
            Reservation(
                reservationResult.totalPrice,
                reservationResult.ticketCount,
                reservationResult.seatNames,
                reservationResult.movieTitle,
                reservationResult.date,
                reservationResult.time
            )
        )
    }

    companion object {
        const val RESERVATION_INTENT_KEY = "RESERVATION_INTENT_KEY"
    }
}
