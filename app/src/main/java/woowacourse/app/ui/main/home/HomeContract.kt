package woowacourse.app.ui.main.home

import woowacourse.app.model.HomeData
import woowacourse.app.usecase.main.MainUseCase

interface HomeContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        val mainUseCase: MainUseCase
        fun getHomeData(): List<HomeData>
    }
}
