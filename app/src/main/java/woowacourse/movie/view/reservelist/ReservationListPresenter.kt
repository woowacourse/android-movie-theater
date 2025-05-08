package woowacourse.movie.view.reservelist

import woowacourse.movie.data.MovieTheaterDatabase
import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.Ticket
import kotlin.concurrent.thread

class ReservationListPresenter(val view: ReservationListContract.View) : ReservationListContract.Presenter {
    override fun loadData() {
        val ticketDao = MovieTheaterDatabase.db.ticketDao()
        var data: List<TicketEntity>? = null
        thread {
            data = ticketDao.findAll()
        }.join()
        val tickets =
            data?.map {
                Ticket(
                    it.title!!,
                    it.showTime!!,
                    it.seats!!,
                    it.reservationCount!!,
                    Cinema(1, it.cinemaName!!),
                )
            } ?: listOf()
        view.showReservationList(tickets)
    }
}
