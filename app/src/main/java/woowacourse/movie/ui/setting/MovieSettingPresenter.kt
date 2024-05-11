package woowacourse.movie.ui.setting

import woowacourse.movie.data.preferences.MoviePreferencesUtil
import woowacourse.movie.ui.notification.NotificationContract.KEY_RECEIVE_NOTIFICATION

class MovieSettingPresenter(
    private val view: MovieSettingContract.View,
    private val moviePreferences: MoviePreferencesUtil,
) : MovieSettingContract.Presenter {
    override fun loadInitialSetting() {
        val status = moviePreferences.getBoolean(KEY_RECEIVE_NOTIFICATION)
        view.setInitialSetting(status)
    }

    override fun updateNotificationSelection(isChecked: Boolean) {
        val result = moviePreferences.setBoolean(KEY_RECEIVE_NOTIFICATION, isChecked)
        view.updateSwitchStatus(result)
    }
}
