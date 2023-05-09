package woowacourse.movie.setting

class SettingPresenter(override val view: SettingContract.View) : SettingContract.Presenter {
    init {
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
