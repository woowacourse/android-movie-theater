package woowacourse.movie.booking.complete

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import woowacourse.movie.AlarmReceiver
import woowacourse.movie.ui.model.TicketUiModel

class MovieAlarmScheduler(
    private val context: Context,
    private val alarmTimeProvider: AlarmTimeProvider = TestAlarmTimeProvider(),
) : AlarmScheduler {
    @SuppressLint("ScheduleExactAlarm")
    override fun scheduleAlarm(
        bookingType: String,
        ticket: TicketUiModel,
    ) {
        if (bookingType != BookingType.RESERVATION.name) return

        val calendar = alarmTimeProvider.getAlarmTime(ticket)
        val intent = AlarmReceiver.createIntent(context, ticket)

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent,
        )
    }
}
