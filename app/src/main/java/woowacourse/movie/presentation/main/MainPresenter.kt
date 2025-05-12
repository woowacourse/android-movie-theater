package woowacourse.movie.presentation.main

import android.content.Context
import woowacourse.movie.data.repository.NotificationSettingRepository
import woowacourse.movie.data.repository.SettingRepository

class MainPresenter(
    private val view: MainContract.View,
    context: Context,
    private val settingRepository: SettingRepository =
        NotificationSettingRepository(context),
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
