package woowacourse.movie.feature.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.feature.history.ReservationHistoryFragment.Companion.TICKET_ID
import woowacourse.movie.model.ticket.Ticket
import java.time.ZoneId
import java.time.ZonedDateTime

class ScreeningAlarm(
    private val context: Context,
) {
    private val alarmManager: AlarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    fun generate(ticket: Ticket) {
        val triggerTime: Long = calculateTriggerTime(ticket)
        val pendingIntent: PendingIntent = createPendingIntent(ticket)
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent,
        )
    }

    private fun createPendingIntent(ticket: Ticket): PendingIntent {
        val intent = createIntent(ticket)

        return PendingIntent.getBroadcast(
            context,
            ticket.uid!!.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun calculateTriggerTime(ticket: Ticket): Long {
        val screeningTime = ZonedDateTime.of(ticket.screeningDateTime, ZoneId.systemDefault())
        return screeningTime.minusMinutes(ALARM_INTERVAL).toInstant().toEpochMilli()
    }

    private fun getNotificationTitle(): String = context.getString(R.string.setting_notification_title)

    private fun getNotificationText(ticket: Ticket): String =
        context.getString(
            R.string.notification_channel_description,
            ticket.movieTitle,
        )

    private fun createIntent(ticket: Ticket): Intent {
        val notificationTitle: String = getNotificationTitle()
        val notificationText: String = getNotificationText(ticket)
        return Intent(context, ScreeningNotificationReceiver::class.java)
            .putExtra(TICKET_ID, ticket.uid)
            .putExtra(SCREENING_NOTIFICATION_TITLE, notificationTitle)
            .putExtra(SCREENING_NOTIFICATION_TEXT, notificationText)
    }

    companion object {
        const val SCREENING_NOTIFICATION_TITLE = "Screening Notification Title"
        const val SCREENING_NOTIFICATION_TEXT = "Screening Notification Text"
        private const val ALARM_INTERVAL = 30L
    }
}
