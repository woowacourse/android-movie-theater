package woowacourse.movie.presenter.theater

interface TheaterSelectionContract {
    interface Presenter {
        fun loadTheater(
            movieId: Int,
            theaterId: Int,
        )
    }

    interface View {
        fun navigateToDetail(
            movieId: Int,
            theaterId: Int,
        )
    }
}
