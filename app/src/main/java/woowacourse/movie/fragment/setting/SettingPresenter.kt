package woowacourse.movie.fragment.setting

import com.woowacourse.domain.SettingRepository
import woowacourse.movie.fragment.setting.SettingFragment.Companion.SETTING_PUSH_ALARM_SWITCH_KEY

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingRepository: SettingRepository,
) : SettingContract.Presenter {

    override fun loadSwitchStatus(permission: Boolean) {
        if (!permission) {
            view.setSwitchStatus(false)
        } else {
            view.setSwitchStatus(settingRepository.getValue(SETTING_PUSH_ALARM_SWITCH_KEY))
        }
    }

    override fun updateSwitchStatus(
        permission: Boolean,
        isChecked: Boolean,
        action: () -> Unit,
    ) {
        settingRepository.setValue(SETTING_PUSH_ALARM_SWITCH_KEY, isChecked)
        if (isChecked && !permission) {
            action()
        }
    }
}
