package woowacourse.movie.fragment.setting

import woowacourse.movie.domain.setting.SettingRepository
import woowacourse.movie.fragment.setting.SettingFragment.Companion.SETTING_NOTIFICATION

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingRepository: SettingRepository
) : SettingContract.Presenter {

    override fun initSwitchStatus(permission: Boolean) {
        if (!permission) {
            view.setSwitchStatus(false)
        } else {
            view.setSwitchStatus(settingRepository.getValue(SETTING_NOTIFICATION))
        }
    }

    override fun updateSwitchStatus(
        permission: Boolean,
        isChecked: Boolean
    ) {
        settingRepository.setValue(SETTING_NOTIFICATION, isChecked)
        if (isChecked && !permission) {
            view.requestPermission()
        }
    }
}
