package woowacourse.movie.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.domain.model.ReservationInfo
import java.time.LocalDateTime
import java.time.ZoneId

object AlarmHelper {
    fun setAlarm(
        context: Context,
        reservationInfo: ReservationInfo,
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent =
            Intent(context, AlarmReceiver::class.java).putExtra("reservationInfo", reservationInfo)
        val alarmIntent =
            PendingIntent.getBroadcast(
                context,
                reservationInfo.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE,
            )

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            LocalDateTime
                .now()
                .plusSeconds(5)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli(),
//            reservationInfo.reservationDateTime
//                .atZone(ZoneId.systemDefault())
//                .toInstant()
//                .toEpochMilli(),
            alarmIntent,
        )
    }
}
