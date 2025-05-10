package woowacourse.movie.presentation.setting

import woowacourse.movie.data.NotificationRepositoryImpl
import woowacourse.movie.domain.NotificationRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val notificationRepository: NotificationRepository = NotificationRepositoryImpl(),
) : SettingContract.Presenter {
    init {
        view.notifyNotificationEnabled(notificationRepository.notificationEnabled())
    }

    override fun updateNotificationEnabled(isEnabled: Boolean) {
        notificationRepository.updateNotificationEnabled(isEnabled)
        val updatedEnabled = notificationRepository.notificationEnabled()
        view.notifyNotificationEnabled(updatedEnabled)
    }
}
