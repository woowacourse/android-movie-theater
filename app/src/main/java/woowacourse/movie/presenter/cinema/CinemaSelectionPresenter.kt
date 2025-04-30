package woowacourse.movie.presenter.cinema

import woowacourse.movie.contract.cinema.CinemaSelectionContract
import woowacourse.movie.data.cinema.CinemaData
import woowacourse.movie.data.cinema.LocalCinemaData
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy

class CinemaSelectionPresenter(
    private val view: CinemaSelectionContract.View,
    private val screening: Screening,
    cinemaData: CinemaData = LocalCinemaData(),
) : CinemaSelectionContract.Presenter {
    private val cinemas = cinemaData.value
    private val reservableCinemas: List<Cinema>
        get() = cinemas.filter { it.showtimeCount(screening) != 0 }

    override fun presentCinemas() {
        view.setCinemas(reservableCinemas)
    }

    override fun onSelectCinema(
        cinemaName: String,
        showtimePolicy: ShowtimePolicy,
    ) {
        view.navigateToReservationScreen(screening, cinemaName, showtimePolicy)
    }
}
