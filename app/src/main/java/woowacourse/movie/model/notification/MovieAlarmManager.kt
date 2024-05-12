package woowacourse.movie.model.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.ui.setting.MovieSettingKey.MOVIE_TITLE_ID
import woowacourse.movie.ui.setting.MovieSettingKey.TICKET_ID
import java.time.LocalDateTime
import java.time.ZoneId

class MovieAlarmManager private constructor(context: Context) {
    private val appContext = context.applicationContext

    fun setAlarm(
        movieTitle: String,
        userTicketId: Long,
        screeningStartDateTime: LocalDateTime,
    ) {
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent =
            Intent(appContext, MovieAlarmReceiver::class.java).apply {
                putExtra(MOVIE_TITLE_ID, movieTitle)
                putExtra(TICKET_ID, userTicketId)
            }

        if (isReservationAfterCurrentTime(screeningStartDateTime)) {
            val alarmTime =
                screeningStartDateTime
                    .minusMinutes(ALARM_OFFSET)
                    .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                makePendingIntent(userTicketId.toInt(), intent),
            )
        }
    }

    fun cancelAlarm(requestCode: Int) {
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(appContext, MovieAlarmReceiver::class.java)
        alarmManager.cancel(makePendingIntent(requestCode, intent))
    }

    private fun makePendingIntent(
        requestCode: Int,
        intent: Intent,
    ): PendingIntent =
        PendingIntent.getBroadcast(
            appContext,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

    private fun isReservationAfterCurrentTime(screeningStartDateTime: LocalDateTime): Boolean {
        return screeningStartDateTime.isAfter(LocalDateTime.now())
    }

    companion object {
        private const val ALARM_OFFSET = 30L
        private const val ERROR_INSTANCE = "인스턴스가 초기화 되지 않았습니다."
        private var instance: MovieAlarmManager? = null

        fun get(): MovieAlarmManager {
            return instance ?: throw IllegalStateException(ERROR_INSTANCE)
        }

        fun initialize(context: Context) {
            instance = MovieAlarmManager(context)
        }
    }
}
