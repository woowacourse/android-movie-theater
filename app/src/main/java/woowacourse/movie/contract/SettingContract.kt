package woowacourse.movie.contract

import woowacourse.movie.system.Setting

interface SettingContract {
    interface View {
        val presenter: Presenter
        fun makeSettingSwitch()
        fun onNotificationSwitchCheckedChangeListener(
            setting: Setting,
            permission: String,
            isChecked: Boolean
        )
    }

    interface Presenter {
        val view: View
        fun initFragment()
        fun toggleNotificationSetting(
            setting: Setting,
            permission: String,
            isChecked: Boolean
        )
    }
}
