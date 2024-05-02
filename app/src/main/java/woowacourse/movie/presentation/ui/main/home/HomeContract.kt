package woowacourse.movie.presentation.ui.main.home

import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.presentation.base.BasePresenter
import woowacourse.movie.presentation.base.BaseView
import woowacourse.movie.presentation.ui.main.home.bottom.BottomTheaterActionHandler

interface HomeContract {
    interface View : BaseView {
        fun showScreens(screens: List<ScreenView>)

        fun showBottomTheater(
            theaterCounts: List<TheaterCount>,
            movieId: Int,
        )

        fun navigateToDetail(
            movieId: Int,
            theaterId: Int,
        )
    }

    interface Presenter : BasePresenter, ScreenActionHandler, BottomTheaterActionHandler
}
