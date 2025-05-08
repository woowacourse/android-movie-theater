package woowacourse.movie.reservation

import android.content.Context
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Ticket
import kotlin.concurrent.thread

class TicketListPresenter(
    private val view: TicketListContract.View,
) : TicketListContract.Presenter {
    private val reservations: MutableList<Ticket> = mutableListOf()

    override fun initializeData(context: Context) {
        val db = MovieDatabase.getDatabase(context)
        thread {
            val tickets = db.TicketDao().findTicket()
            reservations.addAll(tickets.map { it.toDomain() })
        }.join()
        view.setUpReservationList(reservations.map { it.toUiModel() })
    }
}
