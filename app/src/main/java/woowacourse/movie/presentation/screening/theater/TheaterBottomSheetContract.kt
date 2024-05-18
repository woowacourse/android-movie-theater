package woowacourse.movie.presentation.screening.theater

import woowacourse.movie.presentation.uimodel.TheaterUiModel

interface TheaterBottomSheetContract {
    interface View {
        fun showTheaterInfo(theatersInfo: List<TheaterUiModel>)
    }

    interface Presenter {
        fun loadTheaters()
    }

    interface ItemListener {
        fun onClick(theaterId: Int)
    }
}
