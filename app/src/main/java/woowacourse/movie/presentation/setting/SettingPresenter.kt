package woowacourse.movie.presentation.setting

import woowacourse.movie.data.SettingRepositoryImpl
import woowacourse.movie.domain.SettingRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingRepository: SettingRepository = SettingRepositoryImpl(),
) : SettingContract.Presenter {
    init {
        view.notifyNotificationEnabled(settingRepository.isNotificationEnabled())
    }

    override fun updateNotificationEnabled(isEnabled: Boolean) {
        settingRepository.updateNotificationEnabled(isEnabled)
        val updatedEnabled = settingRepository.isNotificationEnabled()
        view.notifyNotificationEnabled(updatedEnabled)
    }
}
