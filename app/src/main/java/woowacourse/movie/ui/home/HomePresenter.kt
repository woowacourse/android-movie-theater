package woowacourse.movie.ui.home

import woowacourse.movie.domain.model.ScreenAd
import woowacourse.movie.domain.repository.DummyAdvertisement
import woowacourse.movie.domain.repository.DummyTheaters
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.domain.repository.TheaterRepository
import woowacourse.movie.ui.toPreviewUI

class HomePresenter(
    private val view: HomeContract.View,
    private val movieRepository: MovieRepository,
    private val screenRepository: ScreenRepository,
    private val adRepository: DummyAdvertisement = DummyAdvertisement(),
    private val theaterRepository: TheaterRepository = DummyTheaters(),
) : HomeContract.Presenter {
    override fun loadScreen() {
        val screens =
            screenRepository.load()
                .map { screen -> screen.toPreviewUI(image = movieRepository.imageSrc(screen.movie.id)) }.toMutableList()

        val ad = adRepository.load()

        val list = mutableListOf<ScreenAd>()

        (1..screens.size + screens.size / 3).map {
            if (it % 4 == 0) {
                list.add(ad)
            } else {
                list.add(screens.removeFirst())
            }
        }

        view.showScreens(list.toList())
    }

    override fun loadTheaters(screenId: Int) {
        val screen = screenRepository.findById(screenId).getOrThrow()
        val theaters = theaterRepository.loadAll()

        view.showTheaters(screen, theaters.screeningTheater(screen))
    }
}
