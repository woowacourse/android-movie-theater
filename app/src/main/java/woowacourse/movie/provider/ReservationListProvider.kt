package woowacourse.movie.provider

import androidx.fragment.app.Fragment
import woowacourse.movie.provider.RepositoryProvider.ticketRepository
import woowacourse.movie.view.reservelist.ReservationListContract
import woowacourse.movie.view.reservelist.ReservationListPresenter

object ReservationListProvider {
    fun <T> reservationListPresenter(view: T) where T : ReservationListContract.View, T : Fragment =
        ReservationListPresenter(
            view,
            ticketRepository(view.requireContext().applicationContext),
        )
}
