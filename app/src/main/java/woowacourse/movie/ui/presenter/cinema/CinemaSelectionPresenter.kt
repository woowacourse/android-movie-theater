package woowacourse.movie.ui.presenter.cinema

import woowacourse.movie.data.cinema.CinemaData
import woowacourse.movie.data.cinema.LocalCinemaData
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy
import woowacourse.movie.ui.contract.cinema.CinemaSelectionContract
import java.time.LocalDateTime

class CinemaSelectionPresenter(
    private val view: CinemaSelectionContract.View,
    private val screening: Screening,
    cinemaData: CinemaData = LocalCinemaData(),
) : CinemaSelectionContract.Presenter {
    private val cinemas = cinemaData.value
    private val reservableCinemas: List<Cinema>
        get() = cinemas.filter { it.showtimeCount(LocalDateTime.now()) != 0 }

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
