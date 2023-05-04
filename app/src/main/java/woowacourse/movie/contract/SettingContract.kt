package woowacourse.movie.contract

interface SettingContract {
    interface View {
        val presenter: Presenter
        fun makeSettingSwitch()
        fun onNotificationSwitchCheckedChangeListener(
            permission: String,
            isChecked: Boolean
        )
    }

    interface Presenter {
        val view: View
        fun initFragment()
        fun toggleNotificationSetting(
            permission: String,
            isChecked: Boolean
        )
    }
}
