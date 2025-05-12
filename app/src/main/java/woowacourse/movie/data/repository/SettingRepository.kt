package woowacourse.movie.data.repository

import android.content.Context
import woowacourse.movie.data.MovieSharedPreferences

interface SettingRepository {
    fun isSaved(): Boolean

    fun isGranted(): Boolean

    fun saveSettingState(isGranted: Boolean)
}

class NotificationSettingRepository(
    context: Context,
) : SettingRepository {
    private val sharedPrefs = MovieSharedPreferences.getSettings(context)

    override fun isSaved(): Boolean = sharedPrefs.contains(KEY_NOTIFICATION)

    override fun isGranted(): Boolean = sharedPrefs.getBoolean(KEY_NOTIFICATION, false)

    override fun saveSettingState(isGranted: Boolean) {
        with(sharedPrefs.edit()) {
            putBoolean(KEY_NOTIFICATION, isGranted)
            apply()
        }
    }

    companion object {
        private const val KEY_NOTIFICATION = "notification"
    }
}
