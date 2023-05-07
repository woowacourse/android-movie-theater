package woowacourse.movie.storage

interface PushNotificationRepository {
    fun getPushNotification(): Boolean
    fun editPushNotification(isGranted: Boolean)
}
