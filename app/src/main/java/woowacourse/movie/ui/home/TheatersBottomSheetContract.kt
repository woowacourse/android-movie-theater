package woowacourse.movie.ui.home

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theaters

interface TheatersBottomSheetContract {
    interface View {
        fun showTheaters(
            screen: Screen,
            theaters: Theaters,
        )

        fun navigateToScreenDetail(
            screenId: Int,
            theaterId: Int,
        )
    }

    interface Presenter {
        fun saveScreenId(screenId: Int)

        fun loadTheaters()

        fun onTheaterSelected(theaterId: Int)
    }
}
