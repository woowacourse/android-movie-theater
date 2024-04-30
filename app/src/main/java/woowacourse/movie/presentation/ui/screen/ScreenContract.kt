package woowacourse.movie.presentation.ui.screen

import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.presentation.base.BasePresenter
import woowacourse.movie.presentation.base.BaseView
import woowacourse.movie.presentation.ui.screen.bottom.BottomTheaterActionHandler

interface ScreenContract {
    interface View : BaseView {
        fun showScreens(screens: List<ScreenView>)

        fun showBottomTheater(theaterCounts: List<TheaterCount>)

        fun navigateToDetail(id: Int)
    }

    interface Presenter : BasePresenter, ScreenActionHandler, BottomTheaterActionHandler {
        fun loadScreens()
    }
}
