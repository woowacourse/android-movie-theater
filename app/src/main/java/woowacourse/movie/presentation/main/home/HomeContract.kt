package woowacourse.movie.presentation.main.home

import woowacourse.movie.domain.model.home.HomeItem

interface HomeContract {
    interface View {
        val presenter: Presenter

        fun showMoviesInfo(
            items: List<HomeItem>,
        )
    }

    interface Presenter {
        fun setMoviesInfo()
    }
}
