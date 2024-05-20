package woowacourse.movie.ui.home

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.ui.ScreenAd

interface HomeContract {
    interface View {
        fun showScreens(screens: List<ScreenAd>)

        fun showTheatersScreeningMovie(screenData: ScreenData)

        fun showTheatersFail(throwable: Throwable)
    }

    interface Presenter {
        fun loadScreen()

        fun loadTheaters(screenId: Int)
    }
}
