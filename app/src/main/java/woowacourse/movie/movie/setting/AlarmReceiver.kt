package woowacourse.movie.movie.setting

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import woowacourse.movie.movie.MainActivity.Companion.setting_preference_key
import woowacourse.movie.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.movie.seat.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.movie.utils.SettingPreference
import woowacourse.movie.movie.utils.getParcelableCompat

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var bookingMovie: BookingMovieEntity

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ALARM_CODE && SettingPreference.getBoolean(context, setting_preference_key)) {
            val notificationBuilder = NotificationBuilder(context)
            notificationBuilder.createNotificationChannel()

            intent.getParcelableCompat<BookingMovieEntity>(BOOKING_MOVIE_KEY)?.let { bookingMovie = it }
            Log.d(BOOKING_MOVIE_KEY, bookingMovie.toString())

            notificationBuilder.createNotificationBuilder(bookingMovie)
        }
    }

    companion object {
        const val ALARM_CODE = "alarm"
        const val REQUEST_CODE = 925
    }
}
