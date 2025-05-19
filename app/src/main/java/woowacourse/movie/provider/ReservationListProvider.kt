package woowacourse.movie.provider

import android.content.Context
import woowacourse.movie.MovieTheaterApplication
import woowacourse.movie.view.reservelist.ReservationListContract
import woowacourse.movie.view.reservelist.ReservationListPresenter

class ReservationListProvider(context: Context) {
    private val application = context.applicationContext as MovieTheaterApplication

    fun reservationListPresenter(view: ReservationListContract.View): ReservationListPresenter =
        ReservationListPresenter(
            view,
            application.repositoryProvider.ticketRepository,
        )
}
