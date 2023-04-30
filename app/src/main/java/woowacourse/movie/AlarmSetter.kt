package woowacourse.movie

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.ZoneId

object AlarmSetter {

    fun setAlarm(
        context: Context,
        requestCode: Int,
        intent: Intent,
        triggerTime: LocalDateTime
    ) {
        val alarmManager =
            context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        val zone = triggerTime.atZone(ZoneId.systemDefault())
        val mill = zone.toInstant().toEpochMilli()
        alarmManager.set(AlarmManager.RTC_WAKEUP, mill, pendingIntent)
    }
}


