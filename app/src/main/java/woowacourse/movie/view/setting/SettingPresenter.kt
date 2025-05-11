package woowacourse.movie.view.setting

import woowacourse.movie.data.setting.SettingStorageManager

class SettingPresenter(
    private val view: SettingContract.View,
    private val manager: SettingStorageManager,
) : SettingContract.Presenter {
    override fun loadSettings() {
        val notificationEnabled: Boolean = manager.loadNotificationSetting() && view.isNotificationPermitted()
        manager.updateNotificationSetting(notificationEnabled)
        view.showNotificationSetting(notificationEnabled)
    }

    override fun toggleNotificationSetting() {
        val enabled: Boolean = !manager.loadNotificationSetting()
        view.attemptNotificationSettingChange(enabled)
    }

    override fun setNotificationSetting(enabled: Boolean) {
        manager.updateNotificationSetting(enabled)
        view.showNotificationSetting(enabled)
    }
}
