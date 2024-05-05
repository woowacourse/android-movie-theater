package woowacourse.movie.feature.theater

import woowacourse.movie.model.theater.Theater

interface TheaterSelectionContract {
    interface Presenter {
        fun loadTheater()

        fun sendTheaterInfoToReservation(theaterId: Int)
    }

    interface View {
        fun showTheaters(
            theaters: List<Theater>,
            screeningCounts: List<Int>,
        )

        fun navigateToReservation(
            movieId: Int,
            theaterId: Int,
        )
    }
}
