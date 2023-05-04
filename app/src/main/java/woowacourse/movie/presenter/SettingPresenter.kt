package woowacourse.movie.presenter

import woowacourse.movie.contract.SettingContract
import woowacourse.movie.system.Setting

class SettingPresenter(override val view: SettingContract.View) : SettingContract.Presenter {
    override fun initFragment() {
        view.makeSettingSwitch()
    }

    override fun toggleNotificationSetting(
        setting: Setting,
        permission: String,
        isChecked: Boolean
    ) {
        view.onNotificationSwitchCheckedChangeListener(
            setting, permission, isChecked
        )
    }
}
