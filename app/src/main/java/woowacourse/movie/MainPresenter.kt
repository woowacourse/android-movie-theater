package woowacourse.movie

import woowacourse.movie.main.MainContract

class MainPresenter(
    private val view: MainContract.View,
    private val permissionHandler: MoviePermissionHandler,
    private val preferences: PreferencesProvider,
) : MainContract.Presenter {
    override fun requestSettingAlarmPermission() {
        if (view.shouldShowNotificationRationale()) {
            view.showSettingAlarmDialog()
        } else {
            view.requestNotificationPermission()
        }
    }

    override fun requestExactAlarmPermission() {
        if (!permissionHandler.hasExactAlarmPermission()) {
            view.showExactAlarmDialog()
        }
    }

    override fun checkAllPermission() {
        if (permissionHandler.hasAllPermission()) {
            preferences.setAlarmEnabled(true)
        }
    }
}
