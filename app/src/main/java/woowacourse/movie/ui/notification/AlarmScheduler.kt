package woowacourse.movie.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.ui.notification.NotificationContract.ACTION_NOTIFICATION
import java.time.ZoneId
import java.time.ZonedDateTime

class AlarmScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java) as AlarmManager

    fun setSchedule(item: AlarmItem) {
        val intent =
            Intent(context, AlarmReceiver::class.java)
                .setAction(ACTION_NOTIFICATION)
                .putExtra(EXTRA_ID, item.targetId)
                .putExtra(EXTRA_TITLE, item.title)
                .putExtra(EXTRA_SUBTITLE, item.subTitle)

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                ZonedDateTime.of(item.dateTime.minusMinutes(30), ZoneId.systemDefault()).toInstant()
                    .toEpochMilli(),
                pendingIntent,
            ),
            pendingIntent,
        )
    }

    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_TITLE = "title"
        const val EXTRA_SUBTITLE = "subtitle"
    }
}
