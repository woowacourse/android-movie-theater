package woowacourse.app.presentation.ui.main.home

import woowacourse.app.presentation.usecase.main.MainUseCase

class HomePresenter(
    override val mainUseCase: MainUseCase,
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    override fun fetchHomeData() {
        view.initMovies(mainUseCase.getMainData())
    }
}
