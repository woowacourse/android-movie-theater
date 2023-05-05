package woowacourse.movie.presentation.settings

import woowacourse.movie.data.settings.SettingsData

class SettingsPresenter(
    view: SettingsContract.View,
    private val settingsData: SettingsData,
) : SettingsContract.Presenter {

    override var isNotifiable: Boolean
        get() = settingsData.isAvailable
        set(value) {
            settingsData.isAvailable = value
        }

    init {
        view.initNotificationSwitch(settingsData.isAvailable)
    }
}
