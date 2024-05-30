package woowacourse.movie.data.source

interface NotificationPreference {
    fun saveNotificationPreference(enabled: Boolean)

    fun loadNotificationPreference(): Boolean
}
