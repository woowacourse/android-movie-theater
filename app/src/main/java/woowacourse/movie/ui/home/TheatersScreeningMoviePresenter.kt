package woowacourse.movie.ui.home

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.data.repository.ScreenDataSource
import woowacourse.movie.data.repository.TheaterRepository
import woowacourse.movie.domain.model.TheaterScreeningCount

class TheatersScreeningMoviePresenter(
    private val view: TheatersScreeningMovieContract.View,
    private val screenDataSource: ScreenDataSource,
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

    private fun loadedScreen() = screenDataSource.findById(screenId).getOrThrow()

    override fun selectTheater(theaterId: Int) {
        view.showScreenDetail(screenId, theaterId)
    }
}
