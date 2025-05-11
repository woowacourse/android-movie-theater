package woowacourse.movie.view.main.setting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.model.reservation.ReservationInfo
import woowacourse.movie.view.reservation.complete.ReservationCompleteActivity
import woowacourse.movie.view.util.Extras
import woowacourse.movie.view.util.Extras.AlarmData.MOVIE_ALARM_CHANNEL_ID
import woowacourse.movie.view.util.Extras.AlarmData.RESERVATION_INFO_KEY
import woowacourse.movie.view.util.Extras.SettingData.NOTIFICATION_KEY
import woowacourse.movie.view.util.Extras.SettingData.SETTINGS_KEY
import woowacourse.movie.view.util.getParcelableExtraCompat
import java.util.Date

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        Log.d("AlarmTest", "알람 수신됨: ${Date()}")
        val sharedPreferences = context.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE)
        val isNotificationEnabled = sharedPreferences.getBoolean(NOTIFICATION_KEY, false)

        if (!isNotificationEnabled) return
        val reservationInfo =
            intent.getParcelableExtraCompat<ReservationInfo>(RESERVATION_INFO_KEY) ?: return

        showNotification(context, reservationInfo)
    }

    private fun showNotification(
        context: Context,
        reservationInfo: ReservationInfo,
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel =
            NotificationChannel(
                MOVIE_ALARM_CHANNEL_ID,
                context.getString(R.string.alarm_movie_notification_title),
                NotificationManager.IMPORTANCE_HIGH,
            )
        notificationManager.createNotificationChannel(channel)

        val detailIntent =
            Intent(context, ReservationCompleteActivity::class.java).apply {
                putExtra(Extras.ReservationInfoData.RESERVATION_KEY, reservationInfo)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                reservationInfo.id.hashCode(),
                detailIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val notification =
            NotificationCompat
                .Builder(context, MOVIE_ALARM_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_ticket)
                .setContentTitle(context.getString(R.string.alarm_notification_title))
                .setContentText(
                    context.getString(
                        R.string.alarm_notification_message,
                        reservationInfo.title,
                        ALARM_SHOWUP_MINUTE,
                    ),
                ).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        notificationManager.notify(reservationInfo.id.hashCode(), notification)
    }

    companion object {
        const val ALARM_SHOWUP_MINUTE = 30
    }
}
