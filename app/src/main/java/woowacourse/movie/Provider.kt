package woowacourse.movie

import android.view.View
import woowacourse.movie.data.MovieTheaterDatabase
import woowacourse.movie.repository.TicketRepository
import woowacourse.movie.repository.TicketRepositoryImpl
import woowacourse.movie.view.reservelist.ReservationListContract
import woowacourse.movie.view.reservelist.ReservationListPresenter

object Provider {
    fun ticketDao() = MovieTheaterDatabase.db.ticketDao()

    fun ticketRepository(): TicketRepository =
        TicketRepositoryImpl(
            ticketDao(),
        )

    fun reservationListPresenter(view: ReservationListContract.View) =
        ReservationListPresenter(
            view,
            ticketRepository(),
        )
}
