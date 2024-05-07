package woowacourse.movie.presentation.ui.main.home

import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.repository.ScreenRepository

class HomePresenter(
    private val view: HomeContract.View,
    private val repository: ScreenRepository,
) : HomeContract.Presenter {
    override fun fetchScreens() {
        val screens: List<ScreenView> = repository.load()
        view.showScreenList(screens)
    }
}
