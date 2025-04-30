package woowacourse.movie.presenter.cinema

import woowacourse.movie.contract.cinema.CinemaSelectionContract
import woowacourse.movie.data.cinema.CinemaData
import woowacourse.movie.data.cinema.LocalCinemaData
import woowacourse.movie.domain.reservation.Screening

class CinemaSelectionPresenter(
    private val view: CinemaSelectionContract.View,
    cinemaData: CinemaData = LocalCinemaData(),
) : CinemaSelectionContract.Presenter {
    private val cinemas = cinemaData.value

    override fun presentCinemas() {
        view.setCinemas(cinemas)
    }

    override fun onSelectCinema(screening: Screening) {
        view.navigateToReservationScreen(screening)
    }
}
