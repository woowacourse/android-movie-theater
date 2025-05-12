package woowacourse.movie.provider

import android.content.Context
import woowacourse.movie.provider.RepositoryProvider.ticketRepository
import woowacourse.movie.view.movies.reservation.seat.SeatSelectionContract
import woowacourse.movie.view.movies.reservation.seat.SeatSelectionPresenter

object SeatSelectionProvider {
    fun <T> seatSelectionPresenter(view: T) where T : SeatSelectionContract.View, T : Context =
        SeatSelectionPresenter(view, ticketRepository(view.applicationContext))
}
