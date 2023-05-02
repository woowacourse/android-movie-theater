package woowacourse.movie.ui.fragment.settings.presentation

import woowacourse.movie.data.storage.SettingsStorage
import woowacourse.movie.ui.fragment.settings.SettingsContract

class SettingsPresentation : SettingsContract.Presentation {
    override fun changePushNotificationState(enable: Boolean) {
        SettingsStorage.enablePushNotification = enable
    }

    override fun getPushNotificationState(): Boolean = SettingsStorage.enablePushNotification
}
