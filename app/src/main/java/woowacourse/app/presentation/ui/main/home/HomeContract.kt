package woowacourse.app.presentation.ui.main.home

import woowacourse.app.presentation.model.HomeData
import woowacourse.app.presentation.usecase.main.MainUseCase

interface HomeContract {
    interface View {
        val presenter: Presenter
        fun initMovies(homeData: List<HomeData>)
    }

    interface Presenter {
        val mainUseCase: MainUseCase
        fun fetchHomeData()
    }
}
