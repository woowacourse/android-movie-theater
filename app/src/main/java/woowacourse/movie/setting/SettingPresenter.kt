package woowacourse.movie.setting

import woowacourse.movie.data.SettingRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingPreference: SettingRepository,
) : SettingContract.Presenter {
    override fun setPermissionState() {
        view.initAlarmState(settingPreference.isAlarmPermitted())
    }

    override fun updatePermission(isGrant: Boolean) {
        settingPreference.setAlarmPermitted(isGrant)
    }
}
