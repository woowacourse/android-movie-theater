package woowacourse.movie.domain

interface NotificationRepository {
    fun updateNotificationEnabled(isEnabled: Boolean)

    fun notificationEnabled(): Boolean
}
