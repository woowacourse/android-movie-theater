package woowacourse.movie.ui.home

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenAd
import woowacourse.movie.domain.model.Theaters

interface HomeContract {
    interface View {
        fun showScreens(screens: List<ScreenAd>)

        fun showTheaters(
            screen: Screen,
        )
    }

    interface Presenter {
        fun loadScreen()

        fun loadTheaters(screenId: Int)
    }
}
