package woowacourse.movie.feature.setting

import woowacourse.movie.data.setting.AlarmSetting
import woowacourse.movie.util.permission.PermissionChecker

class SettingPresenter(
    private val view: SettingContract.View,
    private val alarmSetting: AlarmSetting,
    private val permissionChecker: PermissionChecker,
) : SettingContract.Presenter {

    override fun loadMovieStartReminderSetting() {
        view.setMovieStartReminderSwitchChecked(alarmSetting.isEnable)
        if (!permissionChecker.hasPermission) {
            requestPermission()
        }
    }

    override fun setMovieStartReminderSettingEnable(value: Boolean) {
        if (!permissionChecker.hasPermission) {
            requestPermission()
            return
        }
        alarmSetting.isEnable = value
    }

    private fun requestPermission() {
        alarmSetting.isEnable = false
        view.setMovieStartReminderSwitchChecked(false)
        view.requestPermission()
    }
}
