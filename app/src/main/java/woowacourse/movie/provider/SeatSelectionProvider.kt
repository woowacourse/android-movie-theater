package woowacourse.movie.provider

import android.content.Context
import woowacourse.movie.MovieTheaterApplication
import woowacourse.movie.view.movies.reservation.seat.SeatSelectionContract
import woowacourse.movie.view.movies.reservation.seat.SeatSelectionPresenter

class SeatSelectionProvider(context: Context) {
    private val application = context.applicationContext as MovieTheaterApplication

    fun seatSelectionPresenter(view: SeatSelectionContract.View): SeatSelectionPresenter {
        return SeatSelectionPresenter(view, application.repositoryProvider.ticketRepository)
    }
}
