package woowacourse.movie.ui.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.model.MovieTicketModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class AlarmCreator(private val context: Context) {
    private val alarmManager: AlarmManager =
        context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

    fun makeAlarm(ticketModel: MovieTicketModel) {
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            setNotificationTime(ticketModel),
            setPendingIntent(ticketModel),
        )
    }

    fun cancelAlarm(ticketModel: MovieTicketModel) {
        alarmManager.cancel(setPendingIntent(ticketModel))
    }

    private fun setNotificationTime(ticketModel: MovieTicketModel): Long {
        val setDateTime: LocalDateTime =
            ticketModel.time.minusMinutes(NOTIFY_BEFORE_TIME)
        val dateTime =
            setDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime()
        return dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    private fun setPendingIntent(ticketModel: MovieTicketModel): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            NotificationCreator.NOTIFICATION_ID,
            ReservationAlarmReceiver.getIntent(ticketModel, context),
            PendingIntent.FLAG_IMMUTABLE,
        )

    companion object {
        private const val NOTIFY_BEFORE_TIME: Long = 30
    }
}
