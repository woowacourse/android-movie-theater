package woowacourse.movie.presentation

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.jvm.java

class AlarmScheduler(
    private val context: Context,
) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun schedule(
        dateTime: LocalDateTime,
        title: String,
    ) {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra("title", title)
            }
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                title.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val triggerAtMillis = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent,
        )
    }
}
