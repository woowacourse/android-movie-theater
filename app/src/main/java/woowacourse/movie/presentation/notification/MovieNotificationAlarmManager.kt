package woowacourse.movie.presentation.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import woowacourse.movie.presentation.notification.MovieNotificationReceiver.Companion.PENDING_REQUEST_CODE
import woowacourse.movie.presentation.reservation.result.ReservationResultActivity.Companion.INTENT_TICKET
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.utils.SharedPreferences
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

object MovieNotificationAlarmManager {
    private const val ALARM_OFFSET_MINUTES = 30L

    fun init(
        context: Context,
        ticketUiModel: MovieTicketUiModel,
    ) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = MovieNotificationReceiver.getIntent(context).apply {
            putExtra(INTENT_TICKET, ticketUiModel)
        }
        val pendingIntent = pendingIntent(context, intent)
        val alarmTime =
            calculateAlarmTime(
                LocalDate.parse(ticketUiModel.screeningDate),
                LocalTime.parse(ticketUiModel.startTime),
            )

        if (canPushAlarm(alarmTime, context)) {
            sendNotification(alarmTime, alarmManager, pendingIntent)
        }
    }

    private fun pendingIntent(
        context: Context,
        intent: Intent,
    ): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            PENDING_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun calculateAlarmTime(
        date: LocalDate,
        time: LocalTime,
    ): Long {
        val dateTime = LocalDateTime.of(date, time)
        val screeningTime = ZonedDateTime.of(dateTime, ZoneId.systemDefault())
        return screeningTime.minusMinutes(ALARM_OFFSET_MINUTES).toInstant().toEpochMilli()
    }

    private fun sendNotification(
        alarmTime: Long,
        alarmManager: AlarmManager,
        pendingIntent: PendingIntent,
    ) {
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
    }

    private fun canPushAlarm(alarmTime: Long, context: Context): Boolean {
        return isFutureTime(alarmTime) && SharedPreferences.getAlarmSetting(context)
    }

    private fun isFutureTime(alarmTime: Long): Boolean {
        return System.currentTimeMillis() <= alarmTime
    }
}
