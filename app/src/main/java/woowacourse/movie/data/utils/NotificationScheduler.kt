package woowacourse.movie.data.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import woowacourse.movie.data.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object NotificationScheduler {
    private const val TAG = "NotificationScheduler"

    fun scheduleNotification(context: Context, movieTicketId: Long, screeningTime: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val canScheduleExactAlarms = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }

        if (canScheduleExactAlarms) {
            try {
                val intent = Intent(context, AlarmReceiver::class.java).apply {
                    putExtra(AlarmReceiver.EXTRA_MOVIE_TICKET_ID, movieTicketId)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context, movieTicketId.toInt(), intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val screeningDateTime = Calendar.getInstance().apply {
                    time = timeFormat.parse(screeningTime) ?: Date()
                    add(Calendar.MINUTE, -30)
                }.timeInMillis

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, screeningDateTime, pendingIntent)
            } catch (e: SecurityException) {
                Log.e(TAG, "보안 예외가 발생합니다: ${e.message}")
            }
        } else {
            Log.w(TAG, "정확한 알람을 예약할 수 없으며 권한이 부여되지 않았습니다.")
        }
    }

    fun cancelNotification(context: Context, movieTicketId: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, movieTicketId.toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}
