package woowacourse.app.presentation.ui.main.home

import woowacourse.app.presentation.model.HomeData

interface HomeContract {
    interface View {
        val presenter: Presenter
        fun initMovies(homeData: List<HomeData>)
    }

    interface Presenter {
        fun fetchHomeData()
    }
}
