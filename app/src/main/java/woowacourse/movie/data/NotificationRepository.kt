package woowacourse.movie.data

interface NotificationRepository {
    fun setNotificationEnabled(enabled: Boolean)

    fun getNotificationEnabled(): Boolean
}
