package woowacourse.movie.view.seatselection

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.view.ReservationCompletedActivity
import woowacourse.movie.view.model.ReservationUiModel

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val reservation = intent.getParcelableCompat<ReservationUiModel>(RESERVATION)
        val content = reservation?.let {
            context.getString(R.string.screening_after_30_minutes, reservation.title)
        }
        val pendingIntent = reservation?.let {
            ReservationCompletedActivity.getPendingIntent(context, reservation)
        }
        val notification = createNotification(context, content, pendingIntent)
        notify(context, notification)
    }

    private fun notify(context: Context, notification: Notification) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
            return
        }
    }

    private fun createNotification(context: Context, content: String?, pendingIntent: PendingIntent?): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_movie)
            setContentTitle(context.getString(R.string.reservation_noti))
            setContentText(content)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }.build()
    }

    companion object {
        const val NOTIFICATION_ID = 200
        const val CHANNEL_ID = "RESERVATION_CHANNEL"
        const val RESERVATION = "RESERVATION"

        fun newIntent(context: Context, reservation: ReservationUiModel): Intent {
            return Intent(context, AlarmReceiver::class.java).apply {
                putExtra(RESERVATION, reservation)
            }
        }
    }
}
