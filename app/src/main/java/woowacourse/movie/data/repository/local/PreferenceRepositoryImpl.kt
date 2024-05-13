package woowacourse.movie.data.repository.local

import androidx.core.content.edit
import woowacourse.movie.MovieApplication.Companion.sharedPreferences
import woowacourse.movie.domain.repository.PreferenceRepository

class PreferenceRepositoryImpl : PreferenceRepository {
    override fun getNotificationMode(): Boolean = sharedPreferences.getBoolean(KEY_NOTIFICATION_MODE, false)

    override fun saveNotificationMode(mode: Boolean) =
        sharedPreferences.edit(true) {
            putBoolean(KEY_NOTIFICATION_MODE, mode)
        }

    companion object {
        private const val KEY_NOTIFICATION_MODE = "KEY_NOTIFICATION_MODE"
    }
}
