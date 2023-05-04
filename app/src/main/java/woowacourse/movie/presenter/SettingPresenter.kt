package woowacourse.movie.presenter

import woowacourse.movie.contract.SettingContract

class SettingPresenter(override val view: SettingContract.View) : SettingContract.Presenter {
    override fun initFragment() {
        view.makeSettingSwitch()
    }

    override fun toggleNotificationSetting(
        permission: String,
        isChecked: Boolean
    ) {
        view.onNotificationSwitchCheckedChangeListener(
            permission, isChecked
        )
    }
}
