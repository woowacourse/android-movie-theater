package woowacourse.movie.presentation.ui.main.home

import woowacourse.movie.domain.repository.ScreenRepository

class HomePresenter(
    private val view: HomeContract.View,
    private val repository: ScreenRepository,
) : HomeContract.Presenter {
    override fun loadScreens() {
        val screens = repository.load()
        view.showScreens(screens)
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
