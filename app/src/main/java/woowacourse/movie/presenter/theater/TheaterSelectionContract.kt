package woowacourse.movie.presenter.theater

interface TheaterSelectionContract {
    interface Presenter {
        fun loadTheater(theaterId: Int)
    }

    interface View {
        fun navigateToDetail(
            movieId: Int,
            theaterId: Int,
        )
    }
}
