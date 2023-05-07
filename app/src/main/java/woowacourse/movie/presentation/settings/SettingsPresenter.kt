package woowacourse.movie.presentation.settings

import woowacourse.movie.data.settings.SettingsData

class SettingsPresenter(
    private val view: SettingsContract.View,
    private val settingsData: SettingsData,
) : SettingsContract.Presenter {

    override var isNotifiable: Boolean
        get() = settingsData.isAvailable
        set(value) {
            settingsData.isAvailable = value
        }

    override fun initNotifiable() {
        view.initNotificationSwitch(settingsData.isAvailable)
    }
}
