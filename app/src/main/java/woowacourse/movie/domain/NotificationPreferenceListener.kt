package woowacourse.movie.domain

interface NotificationPreferenceListener {
    fun updateNotificationEnabled(isEnabled: Boolean)

    fun notificationEnabled(): Boolean
}
