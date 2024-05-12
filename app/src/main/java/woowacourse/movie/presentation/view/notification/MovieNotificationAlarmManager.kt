package woowacourse.movie.presentation.view.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

object MovieNotificationAlarmManager {
    private const val ALARM_OFFSET_MINUTES = 30L
    const val NOTIFICATION_TITLE = "title"
    const val NOTIFICATION_TICKET_ID = "description"

    fun init(
        context: Context,
        ticketUiModel: MovieTicketUiModel,
    ) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, MovieNotificationReceiver::class.java).apply {
            putExtra(NOTIFICATION_TITLE, ticketUiModel.title)
            putExtra(NOTIFICATION_TICKET_ID, ticketUiModel.ticketId)
        }

        val pendingIntent = pendingIntent(context, intent)
        val alarmTime =
            calculateAlarmTime(
                LocalDate.parse(ticketUiModel.screeningDate),
                LocalTime.parse(ticketUiModel.startTime),
            )
        sendNotification(alarmTime, alarmManager, pendingIntent)
    }

    // 브로드캐스트 메시지로 보낼 PendingIntent, 알림이 클릭되었을 때 실행될 액티비티를 위한 인텐트 생성
    private fun pendingIntent(
        context: Context,
        intent: Intent,
    ): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            MovieNotificationReceiver.PENDING_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    // 알림을 표시할 시간을 계산
    private fun calculateAlarmTime(
        date: LocalDate,
        time: LocalTime,
    ): Long {
        val dateTime = LocalDateTime.of(date, time)
        val screeningTime = ZonedDateTime.of(dateTime, ZoneId.systemDefault())
        return screeningTime.minusMinutes(ALARM_OFFSET_MINUTES).toInstant().toEpochMilli()
    }


    // 알림 설정하고 알림 보내기
    private fun sendNotification(
        alarmTime: Long,
        alarmManager: AlarmManager,
        pendingIntent: PendingIntent,
    ) {
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
    }
}
