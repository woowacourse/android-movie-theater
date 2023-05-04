package woowacourse.movie.presentation.settings

import woowacourse.movie.data.settings.SettingsData

class SettingsPresenter(
    view: SettingsContract.View,
    private val settingsData: SettingsData,
) : SettingsContract.Presenter {

    init {
        view.initNotificationSwitch(settingsData.isAvailable)
    }

    override fun setNotifiable(isNotifiable: Boolean) {
        settingsData.isAvailable = isNotifiable
    }
}
