package woowacourse.movie.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import woowacourse.movie.domain.model.AlarmTime
import woowacourse.movie.ui.reservation.ReservationCompleteActivity
import java.time.LocalDateTime

class MovieReservationNotificationRepository(
    private val context: Context,
    private val receiverClass: Class<out BroadcastReceiver>,
    private val alarmTime: AlarmTime,
) :
    NotificationRepository {
    override fun register(
        id: Int,
        dateTime: LocalDateTime,
    ) {
        if (isAlarmAlreadyRegistered(id)) {
            return
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = createPendingIntent(id)

        val time = alarmTime.calculated(dateTime, ALARM_TIME_MINUTE) // 그냥 localDatetime 으로 받자.

        if (time != null) {
            setAlarm(alarmManager, time, pendingIntent)
            return
        }
    }

    private fun createPendingIntent(id: Int): PendingIntent {
        val intent =
            Intent(context, receiverClass).apply {
                putExtra(ReservationCompleteActivity.PUT_EXTRA_KEY_RESERVATION_TICKET_ID, id)
            }
        return PendingIntent.getBroadcast(
            context,
            movieReservationRequestCode(id),
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun setAlarm(
        alarmManager: AlarmManager,
        alarmTime: Long,
        pendingIntent: PendingIntent,
    ) {


        val alarmSetting = AlarmSetting(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)

        when (Build.VERSION.SDK_INT) {
            in Build.VERSION_CODES.S..Int.MAX_VALUE -> {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(alarmSetting)
                }
            }

            else -> alarmManager.set(alarmSetting)
        }
    }

    private fun isAlarmAlreadyRegistered(id: Int): Boolean {
        val intent = Intent(context, receiverClass).apply {
            putExtra(ReservationCompleteActivity.PUT_EXTRA_KEY_RESERVATION_TICKET_ID, id)
        }

        val existingIntent = PendingIntent.getBroadcast(
            context,
            movieReservationRequestCode(id),
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        return existingIntent != null
    }

    companion object {
        private const val ALARM_TIME_MINUTE = 30

        private fun movieReservationRequestCode(id: Int): Int = id
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
