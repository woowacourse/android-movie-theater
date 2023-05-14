package woowacourse.movie.ui.fragment.settings.presenter

import woowacourse.movie.data.storage.SettingsStorage
import woowacourse.movie.ui.fragment.settings.SettingsContract

class SettingsPresenter(
    private val settingsStorage: SettingsStorage,
    private val view: SettingsContract.View
) : SettingsContract.Presenter {
    override fun changePushNotificationState(enable: Boolean) {
        settingsStorage.enablePushNotification = enable
    }

    override fun checkPushNotificationState() {
        val enable = settingsStorage.enablePushNotification
        view.setPushNotificationState(enable)
    }
}
