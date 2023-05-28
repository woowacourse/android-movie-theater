package woowacourse.movie.presentation.settings

import woowacourse.movie.model.data.storage.SettingStorage

class SettingsPresenter(
    override val view: SettingsContract.View,
    private val settingStorage: SettingStorage
) : SettingsContract.Presenter {

    init {
        getNotificationSettings()
    }

    override fun setNotificationSettings(notifyState: Boolean) {
        if (settingStorage.setNotificationSettings(notifyState)) view.setSwitchSelectedState(
            notifyState
        )
    }

    override fun getNotificationSettings() {
        view.setSwitchSelectedState(settingStorage.getNotificationSettings())
    }
}
