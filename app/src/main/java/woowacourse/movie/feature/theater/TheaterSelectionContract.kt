package woowacourse.movie.feature.theater

interface TheaterSelectionContract {
    interface Presenter {
        fun sendTheaterInfoToReservation(theaterId: Int)
    }

    interface View {
        fun navigateToReservation(
            movieId: Int,
            theaterId: Int,
        )
    }
}
