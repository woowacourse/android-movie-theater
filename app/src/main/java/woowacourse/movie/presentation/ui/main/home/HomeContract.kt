package woowacourse.movie.presentation.ui.main.home

import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.presentation.base.BaseView

interface HomeContract {
    interface View : ScreenActionHandler, BaseView {
        fun showScreenList(screens: List<ScreenView>)
    }

    interface Presenter {
        fun fetchScreens()

//        fun selectMovie(movieId: Int)
    }
}
