package woowacourse.movie.presentation.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import woowacourse.movie.R
import woowacourse.movie.presentation.common.model.TicketUiModel
import java.time.LocalDateTime
import java.time.ZoneId

object AlarmHelper {
    const val CHANNEL_ID = "reservation_alarm_channel"
    const val KEY_TICKET = "ticket"

    fun createNotificationChannel(context: Context) {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = context.getString(R.string.notification_channel_description)
            }
        val manager = context.getNotificationManager()
        manager.createNotificationChannel(channel)
    }

    fun setAlarm(
        context: Context,
        ticket: TicketUiModel,
    ) {
        if (!canScheduleExactAlarms(context)) {
            requestExactAlarmPermission(context)
            return
        }

        val alarmManager = context.getAlarmManager()
        val pendingIntent = createAlarmPendingIntent(context, ticket)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            ticket.dateTime.toMillisBeforeMinutes(minutes = 30),
            pendingIntent,
        )
    }

    private fun canScheduleExactAlarms(context: Context): Boolean {
        val alarmManager = context.getAlarmManager()
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()
    }

    private fun requestExactAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    private fun createAlarmPendingIntent(
        context: Context,
        ticket: TicketUiModel,
    ): PendingIntent {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(KEY_TICKET, ticket)
            }

        return PendingIntent.getBroadcast(
            context,
            ticket.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }

    private fun Context.getAlarmManager(): AlarmManager = this.getSystemService(AlarmManager::class.java)

    private fun Context.getNotificationManager(): NotificationManager = this.getSystemService(NotificationManager::class.java)

    private fun LocalDateTime.toMillisBeforeMinutes(
        zoneId: ZoneId = ZoneId.systemDefault(),
        minutes: Long,
    ): Long =
        this
            .minusMinutes(minutes)
            .atZone(zoneId)
            .toInstant()
            .toEpochMilli()
}
