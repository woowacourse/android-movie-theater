package woowacourse.movie.data.local.adapter

import android.content.Context
import woowacourse.movie.data.local.database.MovieDatabase.Companion.getMovieDatabase
import woowacourse.movie.data.local.entity.TicketEntity
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.view.data.TicketDataAdapter

class TicketAdapter(private val context: Context) : TicketDataAdapter {
    private val dao by lazy { getMovieDatabase(context).ticketDao() }

    override fun insert(ticket: Ticket) {
        dao.insert(ticket.toTicketEntity())
    }

    private fun Ticket.toTicketEntity() =
        TicketEntity(
            title = title,
            count = count,
            showtime = showtime,
        )
}
