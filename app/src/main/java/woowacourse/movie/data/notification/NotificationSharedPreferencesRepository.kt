package woowacourse.movie.data.notification

import android.content.Context
import woowacourse.movie.data.MovieSharedPreferences
import kotlin.concurrent.Volatile

class NotificationSharedPreferencesRepository private constructor(context: Context): NotificationRepository {
    private val movieSharedPreferences by lazy { MovieSharedPreferences.instance(context) }

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

        fun instance(context: Context): NotificationSharedPreferencesRepository {
            return instance ?: synchronized(this) {
                val newInstance = NotificationSharedPreferencesRepository(context)
                instance = newInstance
                newInstance
            }
        }
    }
}
