package woowacourse.movie.presentation.setting

import woowacourse.movie.domain.NotificationPreferenceRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val notificationPreferenceRepository: NotificationPreferenceRepository,
) : SettingContract.Presenter {
    init {
        view.notifyNotificationEnabled(notificationPreferenceRepository.notificationEnabled())
    }

    override fun updateNotificationEnabled(isEnabled: Boolean) {
        notificationPreferenceRepository.updateNotificationEnabled(isEnabled)
        val updatedEnabled = notificationPreferenceRepository.notificationEnabled()
        view.notifyNotificationEnabled(updatedEnabled)
    }
}
