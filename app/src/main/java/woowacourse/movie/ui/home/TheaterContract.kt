package woowacourse.movie.ui.home

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theaters

interface TheaterContract {
    interface View {
        fun initTheaterAdapter(screen: Screen)

        fun showTheaters(
            screen: Screen,
            theaters: Theaters,
        )
    }

    interface Presenter {
        fun saveScreenId(screenId: Int)

        fun initTheaterAdapter()

        fun loadTheaters()
    }
}
