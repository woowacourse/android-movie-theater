package woowacourse.movie.presentation.settings

import android.content.Context
import woowacourse.movie.data.repository.NotificationSettingRepository
import woowacourse.movie.data.repository.SettingRepository

class SettingsPresenter(
    private val view: SettingsContract.View,
    context: Context,
    private val settingRepository: SettingRepository =
        NotificationSettingRepository(context),
) : SettingsContract.Presenter {
    override fun loadSettings() {
        val isNotificationEnabled = settingRepository.isGranted()
        view.updateNotificationSetting(isNotificationEnabled)
    }

    override fun saveNotificationSetting(isEnabled: Boolean) {
        settingRepository.saveSettingState(isEnabled)
    }
}
