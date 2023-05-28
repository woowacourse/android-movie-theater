package woowacourse.movie.feature.setting

import woowacourse.movie.data.setting.AlarmSetting
import woowacourse.movie.util.permission.PermissionChecker

class SettingPresenter(
    private val view: SettingContract.View,
    private val alarmSetting: AlarmSetting,
    private val permissionChecker: PermissionChecker,
) : SettingContract.Presenter {

    override fun setMovieReminderChecked() {
        view.setMovieReminderChecked(alarmSetting.isEnable)
        if (!permissionChecker.hasPermission) requestPermission()
    }

    override fun changeMovieReminderChecked(switchChecked: Boolean) {
        if (!permissionChecker.hasPermission) {
            requestPermission()
            return
        }
        alarmSetting.isEnable = switchChecked
    }

    private fun requestPermission() {
        alarmSetting.isEnable = false
        view.setMovieReminderChecked(false)
        view.requestPermission()
    }
}
