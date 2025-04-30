package woowacourse.movie.presentation.view.movies

import woowacourse.movie.domain.model.cinema.Theaters
import woowacourse.movie.presentation.fixture.createDummyMovies
import woowacourse.movie.presentation.fixture.dummyTheaters
import woowacourse.movie.presentation.model.toUiModel

class MoviesPresenter(
    private val view: MoviesContract.View,
) : MoviesContract.Presenter {
    private val theaters: Theaters = dummyTheaters

    override fun fetchData() {
        val movies = createDummyMovies(10_000).map { it.toUiModel() }
        view.showScreen(movies)
    }

    override fun availableTheatersAndCount(movieId: Int) {
        val theaterSchedules = theaters.findTheatersByMovieId(movieId)
        val theaterScheduleCounts =
            theaterSchedules.mapValues { (_, schedules) ->
                schedules.size
            }
        view.showAvailableTheatersAndCount(theaterScheduleCounts)
    }
}
