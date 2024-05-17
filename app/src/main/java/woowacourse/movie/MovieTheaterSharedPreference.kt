package woowacourse.movie

import android.content.Context
import androidx.core.content.edit

class MovieTheaterSharedPreference private constructor(context: Context) {
    private val mTHSharedPreference by lazy {
        context.getSharedPreferences(MOVIE_THEATER_NAME, Context.MODE_PRIVATE)
    }

    var notificationEnabled
        set(value) =
            mTHSharedPreference.edit {
                putBoolean(NOTIFICATION_ENABLED_KEY, value)
                apply()
            }
        get() = mTHSharedPreference.getBoolean(NOTIFICATION_ENABLED_KEY, DEFAULT_NOTIFICATION_ENABLED)

    companion object {
        private const val MOVIE_THEATER_NAME = "movie_theater"
        private const val NOTIFICATION_ENABLED_KEY = "reservation_notification_key"
        private const val DEFAULT_NOTIFICATION_ENABLED = false

        @Volatile
        private var instance: MovieTheaterSharedPreference? = null

        fun getInstance(context: Context): MovieTheaterSharedPreference {
            return instance ?: synchronized(this) {
                instance ?: MovieTheaterSharedPreference(context)
            }
        }
    }
}
