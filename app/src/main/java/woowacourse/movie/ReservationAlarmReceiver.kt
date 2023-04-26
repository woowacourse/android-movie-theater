package woowacourse.movie

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.activity.ReservationResultActivity
import woowacourse.movie.activity.SeatSelectionActivity
import woowacourse.movie.fragment.SettingFragment
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.error.BroadcastReceiverError.returnWithError
import woowacourse.movie.view.error.ViewError
import woowacourse.movie.view.getSerializable

class ReservationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SeatSelectionActivity.ACTION_ALARM &&
            Setting.getSettingValue(context, SettingFragment.SETTING_NOTIFICATION)
        ) {
            val reservation =
                intent.extras?.getSerializable<ReservationViewData>(ReservationViewData.RESERVATION_EXTRA_NAME)
                    ?: return returnWithError(ViewError.MissingExtras(ReservationViewData.RESERVATION_EXTRA_NAME))

            val reservationIntent = ReservationResultActivity.from(context, reservation).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                SeatSelectionActivity.RESERVATION_REQUEST_CODE,
                reservationIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val builder =
                NotificationCompat.Builder(context, RESERVATION_NOTIFICATION_CHANNEL_ID).apply {
                    setSmallIcon(R.drawable.ic_launcher_foreground)
                    setContentTitle(context.getString(R.string.notification_content_title))
                    setContentText(
                        context.getString(
                            R.string.notification_content_text,
                            reservation.movie.title
                        )
                    )
                    priority = NotificationCompat.PRIORITY_DEFAULT
                    setContentIntent(pendingIntent)
                    setAutoCancel(true)
                }

            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        context, Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    notify(NOTIFICATION_ID, builder.build())
                }
            }
        }
    }

    companion object {
        const val RESERVATION_NOTIFICATION_CHANNEL_ID = "reservation"
        const val NOTIFICATION_ID = 5
    }
}
