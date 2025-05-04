package woowacourse.movie.presentation.home.movies

import woowacourse.movie.domain.model.cinema.Theaters
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.presentation.common.fixture.createDummyMovies
import woowacourse.movie.presentation.common.fixture.dummyTheaters
import woowacourse.movie.presentation.common.model.TheatersUiModel
import woowacourse.movie.presentation.common.model.toUiModel
import java.time.LocalDateTime

class MoviesPresenter(
    private val view: MoviesContract.View,
) : MoviesContract.Presenter {
    private var movies: List<Movie> = listOf()
    private val theaters: Theaters = dummyTheaters

    override fun fetchData() {
        movies = createDummyMovies(10_000).map { it }
        view.showScreen(movies.map { it.toUiModel() })
    }

    override fun availableTheatersAndCount(movieId: Int) {
        val theaterSchedules = theaters.findTheatersByMovieId(movieId, LocalDateTime.now())
        val movie = movies.find { it.id == movieId }?.toUiModel() ?: return

        view.showAvailableTheatersAndCount(movie, TheatersUiModel(theaterSchedules))
    }
}
