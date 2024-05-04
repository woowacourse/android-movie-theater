package woowacourse.movie.presenter.theater

import woowacourse.movie.model.theater.Theater

interface TheaterSelectionContract {
    interface Presenter {
        fun loadTheaters(movieId: Int)
        fun loadTheater(theaterId: Int)
    }

    interface View {
        fun navigateToDetail(
            movieId: Int,
            theaterId: Int,
        )

        fun showTheaters(theaters: List<Theater>)
    }
}
