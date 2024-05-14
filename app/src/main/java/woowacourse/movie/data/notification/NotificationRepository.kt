package woowacourse.movie.data.notification

interface NotificationRepository {
    fun update(isGrant: Boolean)

    fun isGrant(): Boolean
}
