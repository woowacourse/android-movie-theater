package woowacourse.movie

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.ui.reservationhistorydetail.ReservationHistoryDetailActivity

class MovieReservationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val reservationHistoryId =
            intent.getLongExtra(RESERVATION_HISTORY_ID, DEFAULT_RESERVATION_HISTORY_ID)
        if (reservationHistoryId == DEFAULT_RESERVATION_HISTORY_ID) return
        val notificationTitle =
            intent.getStringExtra(NOTIFICATION_TITLE) ?: DEFAULT_NOTIFICATION_TITLE
        val notificationContent =
            intent.getStringExtra(NOTIFICATION_CONTENT) ?: DEFAULT_NOTIFICATION_CONTENT

        val reservationNotification = ReservationNotification(context)
        val sendIntent =
            ReservationHistoryDetailActivity.newIntent(
                context,
                reservationHistoryId,
            )

        reservationNotification.sendNotification(
            intent = sendIntent,
            title = notificationTitle,
            content = notificationContent,
        )
    }

    companion object {
        private const val RESERVATION_HISTORY_ID = "reservation_history_id"
        private const val NOTIFICATION_TITLE = "notification_title"
        private const val NOTIFICATION_CONTENT = "notification_content"
        private const val DEFAULT_RESERVATION_HISTORY_ID = -1L
        private const val DEFAULT_NOTIFICATION_TITLE = ""
        private const val DEFAULT_NOTIFICATION_CONTENT = ""

        fun newIntent(
            context: Context,
            reservationHistoryId: Long,
            notificationTitle: String,
            notificationContent: String,
        ): Intent {
            return Intent(context, MovieReservationAlarmReceiver::class.java).apply {
                putExtra(RESERVATION_HISTORY_ID, reservationHistoryId)
                putExtra(NOTIFICATION_TITLE, notificationTitle)
                putExtra(NOTIFICATION_CONTENT, notificationContent)
            }
        }
    }
}
