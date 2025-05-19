package woowacourse.movie.view.setting

import woowacourse.movie.data.setting.SettingRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val manager: SettingRepository,
) : SettingContract.Presenter {
    override fun loadSettings() {
        val notificationEnabled: Boolean = manager.isNotificationEnabled() && view.isNotificationPermitted()
        manager.updateNotificationSetting(notificationEnabled)
        view.showNotificationSetting(notificationEnabled)
    }

    override fun toggleNotificationSetting() {
        val enabled: Boolean = !manager.isNotificationEnabled()
        view.attemptNotificationSettingChange(enabled)
    }

    override fun setNotificationSetting(enabled: Boolean) {
        manager.updateNotificationSetting(enabled)
        view.showNotificationSetting(enabled)
    }
}
