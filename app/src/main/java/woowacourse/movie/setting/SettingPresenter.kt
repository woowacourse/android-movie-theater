package woowacourse.movie.setting

import woowacourse.movie.main.permission.MoviePermissionHandler
import woowacourse.movie.main.sharedPreference.PreferencesProvider

class SettingPresenter(
    private val view: SettingContract.View,
    private val permissionHandler: MoviePermissionHandler,
    private val preferences: PreferencesProvider,
) : SettingContract.Presenter {
    override fun setNotificationAlarm(isChecked: Boolean) {
        preferences.setAlarmEnabled(isChecked)
        view.showAlarmState()
    }

    override fun checkAllPermission() {
        if (permissionHandler.hasAllPermission()) {
            preferences.setAlarmEnabled(true)
        }
    }
}
