package woowacourse.movie.presentation.setting

import woowacourse.movie.RepositoryProvider
import woowacourse.movie.domain.SettingRepository

class SettingPresenter private constructor(
    private val view: SettingContract.View,
    private val settingRepository: SettingRepository,
) : SettingContract.Presenter {
    init {
        view.notifyNotificationEnabled(settingRepository.isNotificationEnabled())
    }

    override fun updateNotificationEnabled(isEnabled: Boolean) {
        settingRepository.updateNotificationEnabled(isEnabled)
        val updatedEnabled = settingRepository.isNotificationEnabled()
        view.notifyNotificationEnabled(updatedEnabled)
    }

    companion object {
        fun create(
            view: SettingContract.View,
            settingRepository: SettingRepository = RepositoryProvider.settingRepository,
        ): SettingPresenter {
            return SettingPresenter(view, settingRepository)
        }
    }
}
