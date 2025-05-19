package woowacourse.movie.view.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity.ALARM_SERVICE
import woowacourse.movie.domain.model.ticket.Ticket

class NotificationManagerImpl(
    private val context: Context,
    private val scheduler: NotificationScheduler,
) : NotificationManager {
    override fun setNotification(ticket: Ticket) {
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                ticket.hashCode(),
                NotificationReceiver.newIntent(context, ticket),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            scheduler.notificationTime(ticket),
            pendingIntent,
        )
    }
}
