package woowacourse.movie.ui.settings.presenter

import woowacourse.movie.ui.settings.contract.SettingsContract

class SettingsPresenter(
    private val settingsView: SettingsContract.View,
) : SettingsContract.Presenter {
    private var isNotificationOptionChecked: Boolean = false

    override fun loadSwitchChecked(isNotificationOptionChecked: Boolean) {
        this.isNotificationOptionChecked = isNotificationOptionChecked
    }

    override fun refreshChecked() {
        settingsView.setSwitchChecked(isNotificationOptionChecked)
    }
}
