package woowacourse.movie.presentation.setting

import woowacourse.movie.domain.NotificationPreferenceListener

class SettingPresenter(
    private val view: SettingContract.View,
    private val notificationPreferenceListener: NotificationPreferenceListener,
) : SettingContract.Presenter {
    init {
        view.notifyNotificationEnabled(notificationPreferenceListener.notificationEnabled())
    }

    override fun updateNotificationEnabled(isEnabled: Boolean) {
        notificationPreferenceListener.updateNotificationEnabled(isEnabled)
        val updatedEnabled = notificationPreferenceListener.notificationEnabled()
        view.notifyNotificationEnabled(updatedEnabled)
    }
}
