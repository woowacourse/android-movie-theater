package woowacourse.movie.presenter.cinema

import woowacourse.movie.contract.cinema.CinemaSelectionContract
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.DefaultShowtimePolicy

class CinemaSelectionPresenter(private val view: CinemaSelectionContract.View) :
    CinemaSelectionContract.Presenter {
    private val cinemas =
        listOf(
            Cinema(
                "선릉 극장",
                listOf(),
                DefaultShowtimePolicy(),
            ),
            Cinema(
                "잠실 극장",
                listOf(),
                DefaultShowtimePolicy(),
            ),
            Cinema(
                "강남 극장",
                listOf(),
                DefaultShowtimePolicy(),
            ),
        )

    override fun presentCinemas() {
        view.setCinemas(cinemas)
    }
}
