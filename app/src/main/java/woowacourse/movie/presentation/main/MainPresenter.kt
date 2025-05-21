package woowacourse.movie.presentation.main

import woowacourse.movie.data.repository.SettingRepository

class MainPresenter(
    private val view: MainContract.View,
    private val settingRepository: SettingRepository,
) : MainContract.Presenter {
    override fun checkPermissions() {
        if (!settingRepository.isSaved()) {
            view.requestNotificationPermission()
        }
    }

    override fun saveNotificationSetting(isEnabled: Boolean) {
        settingRepository.saveSettingState(isEnabled)
    }
}
