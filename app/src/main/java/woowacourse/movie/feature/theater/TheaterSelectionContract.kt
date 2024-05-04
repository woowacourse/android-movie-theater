package woowacourse.movie.feature.theater

interface TheaterSelectionContract {
    interface Presenter {
        fun sendTheaterInfoToDetail(theaterId: Int)
    }

    interface View {
        fun navigateToDetail(
            movieId: Int,
            theaterId: Int,
        )
    }
}
