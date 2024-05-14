package woowacourse.movie.data.notification

import woowacourse.movie.data.MovieSharedPreferences
import kotlin.concurrent.Volatile

class NotificationSharedPreferencesRepository private constructor(
    private val movieSharedPreferences: MovieSharedPreferences,
) : NotificationRepository {
    override fun update(isGrant: Boolean) {
        movieSharedPreferences.setBoolean(KEY, isGrant)
    }

    override fun isGrant(): Boolean {
        return movieSharedPreferences.getBoolean(KEY, DEFAULT_VALUE)
    }

    companion object {
        private const val KEY = "notification"
        private const val DEFAULT_VALUE = false

        @Volatile
        private var instance: NotificationSharedPreferencesRepository? = null

        fun instance(movieSharedPreferences: MovieSharedPreferences): NotificationSharedPreferencesRepository {
            return instance ?: synchronized(this) {
                val newInstance = NotificationSharedPreferencesRepository(movieSharedPreferences)
                instance = newInstance
                newInstance
            }
        }
    }
}
