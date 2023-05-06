package woowacourse.app.presentation.ui.main.home

import woowacourse.app.presentation.model.HomeData
import woowacourse.app.presentation.usecase.main.MainUseCase

class HomePresenter(override val mainUseCase: MainUseCase) : HomeContract.Presenter {
    override fun getHomeData(): List<HomeData> {
        return mainUseCase.getMainData()
    }
}
