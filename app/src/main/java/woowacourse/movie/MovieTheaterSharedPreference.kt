package woowacourse.movie

import android.content.Context
import androidx.core.content.edit

class MovieTheaterSharedPreference private constructor(context: Context) {
    private val mTHSharedPreference by lazy {
        context.getSharedPreferences(MOVIE_THEATER_NAME, Context.MODE_PRIVATE)
    }

    var notificationReservationHistory
        set(value) =
            mTHSharedPreference.edit {
                putBoolean(RESERVATION_NOTIFICATION_KEY, value)
                apply()
            }
        get() = mTHSharedPreference.getBoolean(RESERVATION_NOTIFICATION_KEY, false)

    companion object {
        private const val MOVIE_THEATER_NAME = "movie_theater"
        private const val RESERVATION_NOTIFICATION_KEY = "reservation_notification_key"

        @Volatile
        private var instance: MovieTheaterSharedPreference? = null

        fun getInstance(context: Context): MovieTheaterSharedPreference {
            return instance ?: synchronized(this) {
                instance ?: MovieTheaterSharedPreference(context)
            }
        }
    }
}
