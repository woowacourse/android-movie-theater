package woowacourse.movie.ui.pushnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.MovieReservationApplication
import woowacourse.movie.ui.reservation.ReservationCompleteActivity.Companion.DEFAULT_RESERVATION_TICKET_ID
import woowacourse.movie.ui.reservation.ReservationCompleteActivity.Companion.PUT_EXTRA_KEY_RESERVATION_TICKET_ID

class PushNotificationBroadCastReceiver() : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val reservationTicketId = intent.getIntExtra(PUT_EXTRA_KEY_RESERVATION_TICKET_ID, DEFAULT_RESERVATION_TICKET_ID)

        val notificationHandler: NotificationHandler = MovieReservationNotificationHandler(context)

        val pendingIntent = notificationHandler.createPendingIntent(reservationTicketId)
        val notification = notificationHandler.buildNotification(pendingIntent)
        val notificationEnabled = MovieReservationApplication.notificationPreference.loadNotificationPreference()
        if (notificationEnabled) {
            notificationHandler.notify(notification)
        }
    }
}
