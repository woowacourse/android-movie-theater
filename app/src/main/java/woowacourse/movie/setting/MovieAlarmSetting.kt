package woowacourse.movie.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import woowacourse.movie.model.MovieReservation
import java.time.LocalDateTime
import java.time.ZoneId

class MovieAlarmSetting(private val context: Context) : AlarmSetting {
    override fun setAlarm(movieReservation: MovieReservation) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        // TODO 추후 now 에서 movieReservation을 이용한 시간으로 수정
        val triggerTime =
            LocalDateTime.now().plusSeconds(3)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val pendingIntent = alarmPendingIntent(context, movieReservation)
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    }

    override fun cancelAlarm(movieReservation: MovieReservation) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val pendingIntent = alarmPendingIntent(context, movieReservation)
        alarmManager.cancel(pendingIntent)
    }

    private fun alarmPendingIntent(
        context: Context,
        movieReservation: MovieReservation,
    ): PendingIntent {
        val intent =
            AlarmReceiver.newIntent(
                context,
                movieReservation.movie.title,
                movieReservation.id,
            )
        return PendingIntent.getBroadcast(
            context,
            movieReservation.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun isReservationAfterCurrentTime(screenDateTime: LocalDateTime): Boolean {
        return screenDateTime.isAfter(LocalDateTime.now())
    }

    companion object {
        private const val ALARM_OFFSET = 30L
    }
}
