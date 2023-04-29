package woowacourse.movie

import android.app.AlarmManager
import android.app.PendingIntent
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.movie.MovieBookingSeatInfo
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object AlarmSetter {
    private const val MOVIE_RUN_BEFORE_TIME = 30L

    fun setMovieStartBeforeAlarm(
        activity: AppCompatActivity,
        movieBookingSeatInfo: MovieBookingSeatInfo
    ) {
        val alarmManager =
            activity.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val movieReminderIntent = MovieReminder.intent(activity)
        movieReminderIntent.putExtra(BundleKeys.MOVIE_BOOKING_SEAT_INFO_KEY, movieBookingSeatInfo)
        val pendingIntent = PendingIntent.getBroadcast(
            activity,
            MovieReminder.NOTIFICATION_ID,
            movieReminderIntent,
            PendingIntent.FLAG_MUTABLE
        )
        val formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm")
        val movieDateTime = LocalDateTime.parse(
            movieBookingSeatInfo.movieBookingInfo.formatAlarmDate(),
            formatter
        )
        val triggerTime = movieDateTime.minusMinutes(MOVIE_RUN_BEFORE_TIME)
        val zone = triggerTime.atZone(ZoneId.systemDefault())
        val mill = zone.toInstant().toEpochMilli()
        alarmManager.set(AlarmManager.RTC_WAKEUP, mill, pendingIntent)
    }
}


