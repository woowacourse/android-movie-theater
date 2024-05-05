package woowacourse.movie.presentation.ui.main.home

import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.presentation.base.BaseView
import woowacourse.movie.presentation.ui.main.home.bottom.BottomTheaterActionHandler

interface HomeContract {
    interface View : ScreenActionHandler, BottomTheaterActionHandler, BaseView {
        fun showScreenList(screens: List<ScreenView>)

        fun showBottomTheater(
            theaterCounts: List<TheaterCount>,
            movieId: Int,
        )
    }

    interface Presenter {
        fun fetchScreens()

        fun selectMovie(movieId: Int)
    }
}
