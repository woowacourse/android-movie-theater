package woowacourse.movie.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import woowacourse.movie.ui.reservation.ReservationCompleteActivity
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class DefaultNotificationRepository(
    private val context: Context,
    private val receiverClass: Class<out BroadcastReceiver>,
    private val alarmTime: AlarmTime,
) :
    NotificationRepository {
    override fun register(
        id: Int,
        dateTime: LocalDateTime,
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = createPendingIntent(id)

        val time = alarmTime.calculated(dateTime, ALARM_TIME_MINUTE)

        if (time != null) {
            setAlarm(alarmManager, time, pendingIntent)
        }
    }

    private fun createPendingIntent(id: Int): PendingIntent {
        val intent =
            Intent(context, receiverClass).apply {
                putExtra(ReservationCompleteActivity.PUT_EXTRA_KEY_RESERVATION_TICKET_ID, id)
            }
        return PendingIntent.getBroadcast(
            context,
            MOVIE_RESERVATION_REMINDER_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun setAlarm(
        alarmManager: AlarmManager,
        alarmTime: Long,
        pendingIntent: PendingIntent,
    ) {
        val exactAlarmTime = alarmTime - TimeUnit.MINUTES.toMillis(30)
        val alarmSetting = AlarmSetting(AlarmManager.RTC_WAKEUP, exactAlarmTime, pendingIntent)

        when (Build.VERSION.SDK_INT) {
            in Build.VERSION_CODES.S..Int.MAX_VALUE -> {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(alarmSetting)
                }
            }

            else -> alarmManager.set(alarmSetting)
        }
    }

    companion object {
        private const val ALARM_TIME_MINUTE = 30
        const val MOVIE_RESERVATION_REMINDER_REQUEST_CODE = 101
    }
}

data class AlarmSetting(
    val alarmType: Int,
    val alarmTime: Long,
    val pendingIntent: PendingIntent,
)

fun AlarmManager.setExact(alarmSetting: AlarmSetting) {
    setExact(alarmSetting.alarmType, alarmSetting.alarmTime, alarmSetting.pendingIntent)
}

fun AlarmManager.set(alarmSetting: AlarmSetting) {
    set(alarmSetting.alarmType, alarmSetting.alarmTime, alarmSetting.pendingIntent)
}

interface AlarmTime {
    fun calculated(
        dateTime: LocalDateTime,
        alarmTimeMinutesBefore: Int,
    ): Long?
}

class AlarmTimeBeforeMinute : AlarmTime {
    override fun calculated(
        dateTime: LocalDateTime,
        alarmTimeMinutesBefore: Int,
    ): Long? {
        val time = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val alarmTime = time - alarmTimeMinutesBefore * 30 * 1000
        val currentTime = System.currentTimeMillis()

        return if (alarmTime > currentTime) {
            alarmTime
        } else {
            null // 이미 영화 시간이 지났다
        }
    }
}
