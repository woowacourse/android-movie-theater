package woowacourse.movie.system

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.data.ReservationViewData
import woowacourse.movie.data.dataSource.SharedSetting
import woowacourse.movie.error.BroadcastReceiverError.returnWithError
import woowacourse.movie.error.ViewError
import woowacourse.movie.view.activity.ReservationResultActivity
import woowacourse.movie.view.fragment.SettingFragment

class ReservationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        SharedSetting.initWithContext(context)
        if (intent.action == ACTION_ALARM && SharedSetting.getValue(
                SettingFragment.SETTING_NOTIFICATION
            )
        ) {
            val reservation =
                intent.extras?.getSerializableCompat<ReservationViewData>(ReservationViewData.RESERVATION_EXTRA_NAME)
                    ?: return returnWithError(ViewError.MissingExtras(ReservationViewData.RESERVATION_EXTRA_NAME))
            val code = intent.extras?.getInt(RESERVATION_REQUEST_CODE) ?: return returnWithError(
                ViewError.MissingExtras(ReservationViewData.RESERVATION_EXTRA_NAME)
            )

            val pendingIntent = makeNotificationPendingIntent(context, reservation, code)
            val builder = makeNotificationBuilder(context, reservation, pendingIntent)
            notifyNotification(context, builder)
        }
    }

    private fun makeNotificationPendingIntent(
        context: Context,
        reservation: ReservationViewData,
        code: Int
    ): PendingIntent {
        val reservationIntent = ReservationResultActivity.from(context, reservation).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(
            context, code, reservationIntent, PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun makeNotificationBuilder(
        context: Context,
        reservation: ReservationViewData,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, RESERVATION_NOTIFICATION_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(context.getString(R.string.notification_content_title))
            setContentText(
                context.getString(
                    R.string.notification_content_text, reservation.movie.title
                )
            )
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
    }

    @SuppressLint("MissingPermission")
    private fun notifyNotification(context: Context, builder: NotificationCompat.Builder) {
        if (PermissionLauncher.isGranted(context, Manifest.permission.POST_NOTIFICATIONS)) {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        }
    }

    companion object {
        private const val RESERVATION_REQUEST_CODE = "reservationCode"
        const val NOTIFICATION_ID = 5
        const val RESERVATION_NOTIFICATION_CHANNEL_ID = "reservation"
        const val ACTION_ALARM = "actionAlarm"

        fun from(context: Context, reservation: ReservationViewData): PendingIntent {
            val code = (0..1000).random()
            return Intent(ACTION_ALARM).let {
                it.putExtra(ReservationViewData.RESERVATION_EXTRA_NAME, reservation)
                it.putExtra(RESERVATION_REQUEST_CODE, code)
                PendingIntent.getBroadcast(
                    context, code, it, PendingIntent.FLAG_IMMUTABLE
                )
            }
        }
    }
}
