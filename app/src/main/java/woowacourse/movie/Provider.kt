package woowacourse.movie

import woowacourse.movie.data.MovieTheaterDatabase
import woowacourse.movie.repository.TicketRepository
import woowacourse.movie.view.movies.reservation.seat.SeatSelectionContract
import woowacourse.movie.view.movies.reservation.seat.SeatSelectionPresenter
import woowacourse.movie.view.reservelist.ReservationListContract
import woowacourse.movie.view.reservelist.ReservationListPresenter

object Provider {
    fun ticketDao() = MovieTheaterDatabase.db.ticketDao()

    fun ticketRepository(): TicketRepository =
        TicketRepository(
            ticketDao(),
        )

    fun reservationListPresenter(view: ReservationListContract.View) =
        ReservationListPresenter(
            view,
            ticketRepository(),
        )

    fun seatSelectionPresenter(view: SeatSelectionContract.View) = SeatSelectionPresenter(view, ticketRepository())
}
