package woowacourse.movie.presentation.view.screening.theater

import woowacourse.movie.domain.model.Theater

interface TheaterBottomSheetContract {
    interface View {
        fun showTheaterInfo(theaterInfo: List<Pair<Theater, Int>>)

        fun moveToMovieDetail(
            movieId: Int,
            theaterId: Int,
        )
    }

    interface Presenter {
        fun loadTheaters(movieId: Int)

        fun onTheaterClicked(theaterId: Int)
    }
}
