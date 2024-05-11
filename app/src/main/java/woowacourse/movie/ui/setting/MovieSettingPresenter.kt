package woowacourse.movie.ui.setting

import android.util.Log
import woowacourse.movie.model.MoviePreferencesUtil

class MovieSettingPresenter(
    private val view: MovieSettingContract.View,
    private val moviePreferences: MoviePreferencesUtil,
) : MovieSettingContract.Presenter {
    override fun loadInitialSetting() {
        val status = moviePreferences.getBoolean("rcv_notification")
        view.setInitialSetting(status)
    }

    override fun updateNotificationSelection(isChecked: Boolean) {
        val result = moviePreferences.setBoolean("rcv_notification", isChecked)
        view.updateSwitchStatus(result)
    }
}
