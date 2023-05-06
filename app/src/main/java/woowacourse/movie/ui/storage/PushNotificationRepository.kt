package woowacourse.movie.ui.storage

interface PushNotificationRepository {
    fun getPushNotification(): Boolean
    fun editPushNotification(isGranted: Boolean)
}
