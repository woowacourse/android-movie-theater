package woowacourse.movie.presentation.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import woowacourse.movie.presentation.Extras
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmScheduler(
    private val context: Context,
) {
    fun scheduleAlarm(reservationInfo: ReservationInfoUiModel) {
        if (!canScheduleAlarm()) return

        scheduleExactAlarm(reservationInfo)
    }

    fun canScheduleAlarm(): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.S ||
            (context.getSystemService(AlarmManager::class.java).canScheduleExactAlarms())

    private fun scheduleExactAlarm(reservationInfo: ReservationInfoUiModel) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                action = Extras.AlarmData.ACTION_ALARM
                putExtra(Extras.AlarmData.ALARM_RESERVATION_KEY, reservationInfo)
                putExtra(Extras.AlarmData.ALARM_MINUTES_KEY, MINUTES_BEFORE_ALARM)
            }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                reservationInfo.title.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE,
            )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            reservationInfo.dateTime.toEpochMillisBefore(MINUTES_BEFORE_ALARM),
            pendingIntent,
        )
    }

    fun requestExactAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    private fun LocalDateTime.toEpochMillisBefore(minutes: Long): Long =
        this
            .minusMinutes(minutes)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

    companion object {
        const val MINUTES_BEFORE_ALARM = 30L
    }
}
