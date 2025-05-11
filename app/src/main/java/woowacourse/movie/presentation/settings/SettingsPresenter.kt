package woowacourse.movie.presentation.settings

import android.content.Context
import android.content.SharedPreferences
import woowacourse.movie.data.MovieSharedPreferences

class SettingsPresenter(
    private val view: SettingsContract.View,
    applicationContext: Context,
    private val settingsSharedPrefs: SharedPreferences =
        MovieSharedPreferences.getSettingsSharedPrefs(applicationContext),
) : SettingsContract.Presenter {
    override fun loadSettings() {
        val isNotificationEnabled =
            settingsSharedPrefs.getBoolean(MovieSharedPreferences.KEY_NOTIFICATION, false)
        view.updateNotificationSetting(isNotificationEnabled)
    }

    override fun saveNotificationSetting(isEnabled: Boolean) {
        with(settingsSharedPrefs.edit()) {
            putBoolean(MovieSharedPreferences.KEY_NOTIFICATION, isEnabled)
            apply()
        }
    }
}
