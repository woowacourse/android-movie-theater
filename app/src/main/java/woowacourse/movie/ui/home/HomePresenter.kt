package woowacourse.movie.ui.home

import woowacourse.movie.data.repository.DefaultAdvertisementRepository
import woowacourse.movie.data.repository.DefaultMovieRepository
import woowacourse.movie.data.repository.DefaultScreenRepository
import woowacourse.movie.data.repository.ScreenRepository
import woowacourse.movie.data.source.DummyAdvertisementDataSource
import woowacourse.movie.data.source.DummyMovieDataSource
import woowacourse.movie.data.source.DummyScreenDataSource
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.ScreenAndAd
import woowacourse.movie.domain.model.toPreviewUI
import woowacourse.movie.domain.usecase.ScreensAndAdvertisementUseCase
import woowacourse.movie.ui.ScreenAd

class HomePresenter(
    private val view: HomeContract.View,
    private val screenRepository: ScreenRepository =
        DefaultScreenRepository(
            DummyScreenDataSource(),
            DefaultMovieRepository(DummyMovieDataSource()),
        ),
    private val screensAndAdvertisementUseCase: ScreensAndAdvertisementUseCase =
        ScreensAndAdvertisementUseCase(
            screenRepository = screenRepository,
            advertisementRepository = DefaultAdvertisementRepository(DummyAdvertisementDataSource()),
        ),
) : HomeContract.Presenter {
    override fun loadScreen() {
        val screenAdList =
            screensAndAdvertisementUseCase.generatedScreenAndAdvertisement().map {
                when (it) {
                    is ScreenAndAd.Screen -> ScreenAd.ScreenPreviewUi(it.id, it.movie.toPreviewUI(), it.dateRange)
                    is ScreenAndAd.Advertisement -> ScreenAd.Advertisement(it.id, it.image as Image.DrawableImage)
                }
            }
        view.showScreens(screenAdList)
    }

    override fun loadTheaters(screenId: Int) {
        screenRepository.loadScreen(screenId)
            .onSuccess { screen ->
                view.showTheatersScreeningMovie(screen.toPreviewUI())
            }.onFailure { e ->
                view.showTheatersFail(e)
            }
    }
}
