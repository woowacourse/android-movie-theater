package woowacourse.movie.ui.home

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.domain.model.TheaterScreeningCount
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.domain.repository.TheaterRepository

class TheatersScreeningMoviePresenter(
    private val view: TheatersScreeningMovieContract.View,
    private val screenRepository: ScreenRepository,
    private val theaterRepository: TheaterRepository,
    private val screenId: Int,
) : TheatersScreeningMovieContract.Presenter {
    private val loadedScreenData: ScreenData = loadedScreen()

    override fun loadTheaters() {
        val theaters = theaterRepository.loadAll()
        val theatersScreening = theaters.screeningTheater(loadedScreenData.movieData)

        val theaterScreeningCount =
            theatersScreening.theaters.map { theater ->
                TheaterScreeningCount(theater, theater.allScreeningTimeCount(loadedScreenData))
            }

        view.showTheatersScreeningcount(theaterScreeningCount)
    }

    private fun loadedScreen() = screenRepository.findById(screenId).getOrThrow()

    override fun selectTheater(theaterId: Int) {
        view.showScreenDetail(screenId, theaterId)
    }
}
