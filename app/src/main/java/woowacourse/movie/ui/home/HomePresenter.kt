package woowacourse.movie.ui.home

import woowacourse.movie.data.repository.DummyAdvertisement
import woowacourse.movie.data.repository.DummyMovieDataSource
import woowacourse.movie.data.repository.DummyScreenDataSource
import woowacourse.movie.domain.usecase.ScreensAndAdvertisementUseCase

class HomePresenter(
    private val view: HomeContract.View,
    private val screensAndAdvertisementUseCase: ScreensAndAdvertisementUseCase =
        ScreensAndAdvertisementUseCase(
            movieDataSource = DummyMovieDataSource(),
            screenDataSource = DummyScreenDataSource(),
            adRepository = DummyAdvertisement(),
        ),
) : HomeContract.Presenter {
    override fun loadScreen() {
        val screenAdList = screensAndAdvertisementUseCase.generated()

        view.showScreens(screenAdList)
    }

    override fun loadTheaters(screenId: Int) {
        screensAndAdvertisementUseCase.loadedScreens(screenId)
            .onSuccess { screen ->
                view.showTheatersScreeningMovie(screen)
            }.onFailure { e ->
                view.showTheatersFail(e)
            }
    }
}
