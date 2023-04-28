package woowacourse.movie.system

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.util.TimeZone

object BroadcastAlarm {
    fun AppCompatActivity.registerAlarmReceiver(
        broadcastReceiver: BroadcastReceiver,
        action: String
    ) {
        val filter = IntentFilter().apply {
            addAction(action)
        }
        registerReceiver(broadcastReceiver, filter)
    }

    fun AppCompatActivity.setAlarmAtDate(date: LocalDateTime, intent: PendingIntent) {
        val milliseconds = date.atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli()
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, milliseconds, intent)
    }

    fun AppCompatActivity.createNotificationChannel(
        channelId: String,
        channelName: String,
        channelDescription: String
    ) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = channelDescription
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
