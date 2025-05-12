package woowacourse.movie.presentation.setting

import woowacourse.movie.data.notification.NotificationPreference

class SettingPresenter(
    private val view: SettingContract.View,
    private val notificationPreference: NotificationPreference,
) : SettingContract.Presenter {
    override fun loadNotificationSetting() {
        val isEnabled = notificationPreference.isNotificationEnabled()
        view.showNotificationSetting(isEnabled)
    }

    override fun changeNotificationSetting(enabled: Boolean) {
        notificationPreference.setNotificationEnabled(enabled)
    }
}
