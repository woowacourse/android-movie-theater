package woowacourse.movie.ui.home

import woowacourse.movie.data.source.DummyAdvertisementDataSource
import woowacourse.movie.data.source.DummyMovieDataSource
import woowacourse.movie.data.source.DummyScreenDataSource
import woowacourse.movie.domain.usecase.ScreensAndAdvertisementUseCase

class HomePresenter(
    private val view: HomeContract.View,
    private val screensAndAdvertisementUseCase: ScreensAndAdvertisementUseCase =
        ScreensAndAdvertisementUseCase(
            movieDataSource = DummyMovieDataSource(),
            screenDataSource = DummyScreenDataSource(),
            advertisementDataSource = DummyAdvertisementDataSource(),
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
