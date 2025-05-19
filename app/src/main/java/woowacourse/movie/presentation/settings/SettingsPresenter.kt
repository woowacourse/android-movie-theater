package woowacourse.movie.presentation.settings

import woowacourse.movie.data.repository.SettingRepository

class SettingsPresenter(
    private val view: SettingsContract.View,
    private val settingRepository: SettingRepository,
) : SettingsContract.Presenter {
    override fun loadSettings() {
        val isNotificationEnabled = settingRepository.isGranted()
        view.updateNotificationSetting(isNotificationEnabled)
    }

    override fun saveNotificationSetting(isEnabled: Boolean) {
        settingRepository.saveSettingState(isEnabled)
    }
}
