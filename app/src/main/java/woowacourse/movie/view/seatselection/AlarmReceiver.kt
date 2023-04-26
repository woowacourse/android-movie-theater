package woowacourse.movie.view.seatselection

import android.Manifest
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
        val toReservationCompletedIntent = reservation?.let {
            ReservationCompletedActivity.newIntent(context, reservation)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            ReservationCompletedActivity.REQUEST_CODE,
            toReservationCompletedIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_movie)
            setContentTitle(context.getString(R.string.reservation_noti))
            setContentText(content)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(NOTI_ID, builder.build())
            return
        }
    }

    companion object {
        const val NOTI_ID = 200
        const val CHANNEL_ID = "RESERVATION_CHANNEL"
        const val RESERVATION = "RESERVATION"
        const val REQUEST_CODE = "REQUEST_CODE"
        const val ALARM_REQUEST_CODE = 100
    }
}
