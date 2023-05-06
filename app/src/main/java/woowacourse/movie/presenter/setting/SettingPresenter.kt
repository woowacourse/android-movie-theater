package woowacourse.movie.presenter.setting

import woowacourse.movie.contract.setting.SettingContract
import woowacourse.movie.ui.storage.PushNotificationRepository

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
