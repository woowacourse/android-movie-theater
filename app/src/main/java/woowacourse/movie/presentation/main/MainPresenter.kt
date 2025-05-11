package woowacourse.movie.presentation.main

import android.content.Context
import android.content.SharedPreferences
import woowacourse.movie.data.MovieSharedPreferences

class MainPresenter(
    private val view: MainContract.View,
    applicationContext: Context,
    private val settingsSharedPrefs: SharedPreferences =
        MovieSharedPreferences.getSettingsSharedPrefs(applicationContext),
) : MainContract.Presenter {
    override fun checkPermissions() {
        if (!settingsSharedPrefs.contains(MovieSharedPreferences.KEY_NOTIFICATION)) {
            view.requestNotificationPermission()
        }
    }

    override fun saveNotificationSetting(isEnabled: Boolean) {
        with(settingsSharedPrefs.edit()) {
            putBoolean(
                MovieSharedPreferences.KEY_NOTIFICATION,
                isEnabled,
            )
            apply()
        }
    }
}
