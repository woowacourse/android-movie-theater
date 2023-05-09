package woowacourse.movie.view.alarm

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.view.model.ReservationUiModel

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var reservationNotificationManager: ReservationNotificationManager
    override fun onReceive(context: Context, intent: Intent) {
        reservationNotificationManager = ReservationNotificationManager(context)
        val reservation = intent.getParcelableCompat<ReservationUiModel>(RESERVATION)
        reservation?.let {
            reservationNotificationManager.notify(reservation)
        }
    }

    companion object {
        const val RESERVATION = "RESERVATION"
        private const val ALARM_REQUEST_CODE = 100

        fun broadcastPendingIntent(context: Context): PendingIntent = PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        fun createPendingIntent(context: Context, reservation: ReservationUiModel): PendingIntent {
            val alarmReceiverIntent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra(RESERVATION, reservation)
            }
            return PendingIntent.getBroadcast(
                context,
                ALARM_REQUEST_CODE,
                alarmReceiverIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
        }
    }
}
