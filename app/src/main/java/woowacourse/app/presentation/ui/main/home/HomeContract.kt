package woowacourse.app.presentation.ui.main.home

import woowacourse.app.presentation.model.HomeData
import woowacourse.app.presentation.usecase.main.MainUseCase

interface HomeContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        val mainUseCase: MainUseCase
        fun getHomeData(): List<HomeData>
    }
}
