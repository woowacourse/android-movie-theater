package woowacourse.movie.ui.setting

import android.util.Log
import woowacourse.movie.model.MoviePreferencesUtil

class MovieSettingPresenter(
    private val view: MovieSettingContract.View,
    private val moviePreferences: MoviePreferencesUtil,
) : MovieSettingContract.Presenter {
    override fun loadInitialSetting() {
//        val isChecked = if (isNotificationGranted) {
            val status = moviePreferences.getBoolean("rcv_notification")
//        } else {
//            moviePreferences.setBoolean("rcv_notification", false)
//        }
        view.setInitialSetting(status)
    }

    override fun updateNotificationSelection(isChecked: Boolean) {
        val result = moviePreferences.setBoolean("rcv_notification", isChecked)
        Log.i("isEnabled : result", "$result")
        view.updateSwitchStatus(result)
    }
}
