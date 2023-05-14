package woowacourse.movie.setting

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.SettingPreference
import woowacourse.movie.main.MainActivity.Companion.SETTING_PREFERENCE_KEY
import woowacourse.movie.mapper.movie.mapToDomain
import woowacourse.movie.seat.view.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.ticket.model.BookingMovieModel
import woowacourse.movie.utils.getParcelableCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val settingPreference = SettingPreference(context)
        if (intent.action == ALARM_CODE && settingPreference.getBoolean(SETTING_PREFERENCE_KEY)) {
            val notificationBuilder = NotificationBuilder(context)
            notificationBuilder.createNotificationChannel()

            val bookingMovie = intent.getParcelableCompat<BookingMovieModel>(BOOKING_MOVIE_KEY)
                ?.mapToDomain()
            if (bookingMovie != null) {
                notificationBuilder.createNotificationBuilder(bookingMovie)
            }
        }
    }

    companion object {
        const val ALARM_CODE = "alarm"
        const val REQUEST_CODE = 925
    }
}
