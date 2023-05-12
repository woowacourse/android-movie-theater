package woowacourse.movie.view.setting

import woowacourse.movie.storage.PushNotificationRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val pushNotificationRepository: PushNotificationRepository
) : SettingContract.Present {

    override fun setupNotificationState() {
        view.updateNotificationState(pushNotificationRepository.getPushNotification())
    }

    override fun updateNotificationState(isGranted: Boolean) {
        pushNotificationRepository.editPushNotification(isGranted)
    }
}
