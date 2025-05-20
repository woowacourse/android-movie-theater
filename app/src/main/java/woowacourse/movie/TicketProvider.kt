package woowacourse.movie

import woowacourse.movie.domain.TicketRepository

object TicketProvider {
    private var _ticketRepository: TicketRepository? = null
    val ticketRepository: TicketRepository get() = _ticketRepository ?: throw IllegalArgumentException()

    fun initTicketRepository(repository: TicketRepository) {
        _ticketRepository = repository
    }
}
