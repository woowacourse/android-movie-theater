package woowacourse.movie.data.preference

import woowacourse.movie.domain.NotificationPreferenceListener

class NotificationPreferenceListenerImpl(
    private val preferenceManager: PreferenceManager,
) : NotificationPreferenceListener {
    override fun updateNotificationEnabled(isEnabled: Boolean) {
        preferenceManager.isNotificationEnabled = isEnabled
    }

    override fun notificationEnabled(): Boolean = preferenceManager.isNotificationEnabled
}
