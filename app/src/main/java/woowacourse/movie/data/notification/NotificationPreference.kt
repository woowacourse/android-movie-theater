package woowacourse.movie.data.notification

interface NotificationPreference {
    fun isNotificationEnabled(): Boolean

    fun setNotificationEnabled(enabled: Boolean)
}
