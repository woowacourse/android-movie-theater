package woowacourse.movie.fixture.repository

import woowacourse.movie.data.TicketData
import woowacourse.movie.data.repository.TicketRepository
import woowacourse.movie.domain.model.Ticket

class FakeTicketRepository : TicketRepository {
    override fun getAll(): List<Ticket> = TicketData.tickets

    override fun save(ticket: Ticket) {
    }
}
