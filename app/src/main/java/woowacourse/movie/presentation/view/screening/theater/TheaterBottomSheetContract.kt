package woowacourse.movie.presentation.view.screening.theater

import woowacourse.movie.domain.model.Theater

interface TheaterBottomSheetContract {
    interface View {
        fun showTheaterInfo(theatersInfo: List<Pair<Theater, Int>>)

        fun moveToMovieDetail(theaterId: Int)
    }

    interface Presenter {
        fun loadTheaters()

        fun onTheaterClicked(theaterId: Int)
    }
}
