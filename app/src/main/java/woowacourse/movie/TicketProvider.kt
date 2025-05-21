package woowacourse.movie

import woowacourse.movie.data.NotificationRepository
import woowacourse.movie.domain.TicketRepository

object TicketProvider {
    private var _ticketRepository: TicketRepository? = null
    val ticketRepository: TicketRepository get() = _ticketRepository ?: throw IllegalArgumentException()

    private var _notificationRepository: NotificationRepository? = null
    val notificationRepository: NotificationRepository get() = _notificationRepository ?: throw IllegalArgumentException()

    fun initTicketRepository(repository: TicketRepository) {
        _ticketRepository = repository
    }

    fun initNotificationRepository(repository: NotificationRepository) {
        _notificationRepository = repository
    }
}
