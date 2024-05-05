package woowacourse.movie.presentation.ui.main.home

import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.repository.ScreenRepository

class HomePresenter(
    private val view: HomeContract.View,
    private val repository: ScreenRepository,
) : HomeContract.Presenter {
    val screens: List<ScreenView> = repository.load()

    fun fetchScreens() {
        view.showScreenList(screens)
    }

    override fun onScreenClick(id: Int) {
        repository.findTheaterCount(id).onSuccess { theaterCounts ->
            view.showBottomTheater(theaterCounts, id)
        }
    }

    override fun onTheaterClick(
        movieId: Int,
        theaterId: Int,
    ) {
        view.navigateToDetail(movieId, theaterId)
    }
}
