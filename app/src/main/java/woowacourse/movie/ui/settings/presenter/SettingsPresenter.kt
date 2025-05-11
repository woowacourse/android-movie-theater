package woowacourse.movie.ui.settings.presenter

import woowacourse.movie.ui.settings.contract.SettingsContract

class SettingsPresenter(
    private val settingsView: SettingsContract.View,
) : SettingsContract.Presenter {
    private var isChecked: Boolean = false

    override fun loadChecked(isChecked: Boolean) {
        this.isChecked = isChecked
    }

    override fun refreshChecked() {
        settingsView.setSwitchChecked()
    }
}
