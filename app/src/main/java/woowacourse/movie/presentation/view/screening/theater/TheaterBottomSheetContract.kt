package woowacourse.movie.presentation.view.screening.theater

import woowacourse.movie.presentation.uimodel.TheaterUiModel

interface TheaterBottomSheetContract {
    interface View {
        fun showTheaterInfo(theatersInfo: List<TheaterUiModel>)

        fun moveToMovieDetail(theaterId: Int)
    }

    interface Presenter {
        fun loadTheaters()

        fun onTheaterClicked(theaterId: Int)
    }
}
