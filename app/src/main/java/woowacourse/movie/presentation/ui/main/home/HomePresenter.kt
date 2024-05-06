package woowacourse.movie.presentation.ui.main.home

import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.domain.repository.TheaterRepository

class HomePresenter(
    private val view: HomeContract.View,
    private val screenRepository: ScreenRepository,
    private val theaterRepository: TheaterRepository,
) : HomeContract.Presenter {
    override fun loadScreens() {
        val screens = screenRepository.load()
        view.showScreens(screens)
    }

    override fun onScreenClick(id: Int) {
        theaterRepository.findTheaterCount(id).onSuccess { theaterCounts ->
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
