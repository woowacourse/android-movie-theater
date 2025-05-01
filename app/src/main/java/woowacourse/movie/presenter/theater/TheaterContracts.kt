package woowacourse.movie.presenter.theater

import woowacourse.movie.model.theater.TheaterMovieSchedule
import woowacourse.movie.model.theater.TheaterMovieSchedules

interface TheaterContracts {
    interface View {
        fun showTheaterMovieSchedule(theaterMovieSchedules: TheaterMovieSchedules)

        fun showReservationView(theaterMovieSchedule: TheaterMovieSchedule)
    }

    interface Presenter {
        fun updateTheaterMovieSchedules(theaterMovieSchedules: TheaterMovieSchedules)

        fun onReservationRequested(theaterMovieSchedule: TheaterMovieSchedule)
    }
}
