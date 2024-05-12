package woowacourse.movie.data.notification

class FakeNotificationRepository : NotificationRepository {
    private var isGrant = false

    override fun update(isGrant: Boolean) {
        this.isGrant = isGrant
    }

    override fun isGrant(): Boolean {
        return isGrant
    }
}
