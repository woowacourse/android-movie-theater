package woowacourse.movie.data.storage

interface NotificationPermissionStorage {
    val notificationPermission: Boolean

    fun updateNotificationPermission(isGranted: Boolean)
}
