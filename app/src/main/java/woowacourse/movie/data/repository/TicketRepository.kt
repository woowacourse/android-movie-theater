package woowacourse.movie.data.repository

import woowacourse.movie.data.TicketData
import woowacourse.movie.domain.model.Ticket

interface TicketRepository {
    fun fetch(): List<Ticket>
}

class DefaultTicketRepository : TicketRepository {
    override fun fetch(): List<Ticket> = TicketData.tickets
}
