package woowacourse.movie.presentation.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.domain.repository.SettingRepository
import woowacourse.movie.presentation.Extras
import woowacourse.movie.presentation.getParcelableExtraCompat
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.view.reservation.complete.ReservationCompleteActivity

class AlarmReceiver(
    private val settingRepository: SettingRepository = MovieApplication.provideSettingRepository(),
) : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (settingRepository.getNotificationEnabled()) return
        if (intent.action != Extras.AlarmData.ACTION_ALARM) return

        val reservationInfo =
            intent.getParcelableExtraCompat<ReservationInfoUiModel>(
                Extras.AlarmData.ALARM_RESERVATION_KEY,
            ) ?: return
        val minutesBeforeAlarm =
            intent.getLongExtra(
                Extras.AlarmData.ALARM_MINUTES_KEY,
                AlarmScheduler.MINUTES_BEFORE_ALARM,
            )

        createNotificationChannel(context)

        val notification = buildNotification(context, reservationInfo, minutesBeforeAlarm)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(reservationInfo.title.hashCode(), notification)
    }

    private fun createNotificationChannel(context: Context) {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = CHANNEL_DESCRIPTION
            }

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun buildNotification(
        context: Context,
        reservationInfo: ReservationInfoUiModel,
        minutesBeforeAlarm: Long,
    ): Notification {
        val resultIntent =
            Intent(context, ReservationCompleteActivity::class.java).apply {
                putExtra(Extras.ReservationInfoData.RESERVATION_KEY, reservationInfo)
            }

        val pendingIntent =
            TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(resultIntent)
                getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_IMMUTABLE)
            }

        return NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle(context.getString(R.string.notification_title, minutesBeforeAlarm))
            .setContentText(
                context.getString(
                    R.string.notification_text,
                    reservationInfo.title,
                    minutesBeforeAlarm,
                ),
            ).setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    companion object {
        private const val CHANNEL_ID = "movie_alarm_channel"
        private const val CHANNEL_NAME = "영화 예매 알림"
        private const val CHANNEL_DESCRIPTION = "예매된 영화 시작 전 알림"
        private const val REQUEST_CODE = 0
    }
}
