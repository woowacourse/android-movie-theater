package woowacourse.app.ui.main.home

import woowacourse.app.model.HomeData
import woowacourse.app.usecase.main.MainUseCase

class HomePresenter(override val mainUseCase: MainUseCase) : HomeContract.Presenter {
    override fun getHomeData(): List<HomeData> {
        return mainUseCase.getMainData()
    }
}
