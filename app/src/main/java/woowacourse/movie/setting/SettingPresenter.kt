package woowacourse.movie.setting

import woowacourse.movie.data.SettingRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingRepository: SettingRepository,
) : SettingContract.Presenter {
    override fun setPermissionState() {
        view.initAlarmState(settingRepository.isAlarmPermitted())
    }

    override fun updatePermission(isGrant: Boolean) {
        settingRepository.setAlarmPermitted(isGrant)
    }
}
