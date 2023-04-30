package woowacourse.movie.movie

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import woowacourse.movie.movie.activity.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.movie.dto.movie.BookingMovieDto
import woowacourse.movie.movie.utils.getParcelableCompat

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var settingPreference: SettingPreference

    override fun onReceive(context: Context, intent: Intent) {
        settingPreference = SettingPreference(context)
        if (intent.action == ALARM_CODE && settingPreference.setting) {
            val notificationBuilder = NotificationBuilder(context)
            notificationBuilder.createNotificationChannel()
            Log.d("test", "알람 채널 생성 성공")

            val bookingMovie = intent.getParcelableCompat<BookingMovieDto>(BOOKING_MOVIE_KEY)!!
            Log.d(BOOKING_MOVIE_KEY, bookingMovie.toString())
            Log.d("test", "인텐트 받아오기 성공")

            notificationBuilder.createNotificationBuilder(bookingMovie)
        }
    }

    companion object {
        const val ALARM_CODE = "alarm"
        const val ALARM_TIME = 30
        const val REQUEST_CODE = 925
    }
}
