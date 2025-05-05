package woowacourse.movie.presenter.theater

import woowacourse.movie.model.theater.TheaterMovieSchedule
import woowacourse.movie.model.theater.TheaterMovieSchedules

class TheaterPresenter(
    private val view: TheaterContracts.View,
) : TheaterContracts.Presenter {
    override fun updateTheaterMovieSchedules(theaterMovieSchedules: TheaterMovieSchedules) {
        view.showTheaterMovieSchedule(theaterMovieSchedules)
    }

    override fun requestReservation(theaterMovieSchedule: TheaterMovieSchedule) {
        view.showReservationView(theaterMovieSchedule)
    }
}
