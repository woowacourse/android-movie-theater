package woowacourse.movie.domain

interface NotificationPreferenceRepository {
    fun updateNotificationEnabled(isEnabled: Boolean)

    fun notificationEnabled(): Boolean
}
