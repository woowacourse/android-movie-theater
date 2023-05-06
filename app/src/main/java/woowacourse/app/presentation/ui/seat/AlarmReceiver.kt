package woowacourse.app.presentation.ui.seat

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.app.data.pushAlarm.PushAlarmDataSource
import woowacourse.app.data.pushAlarm.SettingSharedPreference
import woowacourse.app.presentation.model.ReservationUiModel
import woowacourse.app.presentation.ui.completed.CompletedActivity
import woowacourse.app.presentation.util.getParcelable
import woowacourse.movie.R

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        if (!isAvailableReceivingAlarm(context)) return
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotification(
            context,
            intent.getParcelable(RESERVATION, ReservationUiModel::class.java) ?: return,
        )
    }

    private fun isAvailableReceivingAlarm(context: Context): Boolean {
        val pushAlarmDataSource: PushAlarmDataSource = SettingSharedPreference(context)
        return pushAlarmDataSource.getPushAlarmState()
    }

    private fun createNotification(context: Context, reservationUiModel: ReservationUiModel) {
        val targetIntent = CompletedActivity.getIntent(context, reservationUiModel).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent: PendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getActivity(
                    context,
                    reservationUiModel.id.toInt(),
                    targetIntent,
                    PendingIntent.FLAG_MUTABLE,
                )
            } else {
                PendingIntent.getActivity(
                    context,
                    reservationUiModel.id.toInt(),
                    targetIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT,
                )
            }

        val builder = NotificationCompat.Builder(context, NotiChannel.BOOKING_ALARM.name).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle("CGV")
            setContentText("영화 상영시간 30분 전입니다.")
            setContentIntent(pendingIntent)
            setChannelId(NotiChannel.BOOKING_ALARM.channelName)
            setAutoCancel(true)
        }
        notificationManager.notify(reservationUiModel.id.toInt(), builder.build())
    }

    companion object {
        private const val RESERVATION = "RESERVATION"
        fun getIntent(context: Context, reservationUiModel: ReservationUiModel): Intent {
            return Intent(context, AlarmReceiver::class.java).apply {
                putExtra(RESERVATION, reservationUiModel)
            }
        }
    }
}
