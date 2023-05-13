package woowacourse.movie.setting

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import domain.BookingMovie
import woowacourse.movie.SettingPreference
import woowacourse.movie.dto.movie.BookingMovieDto
import woowacourse.movie.main.MainActivity.Companion.SETTING_PREFERENCE_KEY
import woowacourse.movie.mapper.movie.mapToDomain
import woowacourse.movie.seat.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.utils.getParcelableCompat

class AlarmReceiver(private val settingPreference: SettingPreference) : BroadcastReceiver() {
    private lateinit var bookingMovie: BookingMovie

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ALARM_CODE && settingPreference.getBoolean(SETTING_PREFERENCE_KEY)) {
            val notificationBuilder = NotificationBuilder(context)
            notificationBuilder.createNotificationChannel()

            intent.getParcelableCompat<BookingMovieDto>(BOOKING_MOVIE_KEY)?.let { bookingMovie = it.mapToDomain() }
            Log.d(BOOKING_MOVIE_KEY, bookingMovie.toString())

            notificationBuilder.createNotificationBuilder(bookingMovie)
        }
    }

    companion object {
        const val ALARM_CODE = "alarm"
        const val REQUEST_CODE = 925
    }
}
