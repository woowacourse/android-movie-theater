package woowacourse.movie.presentation.view.home.movies

import woowacourse.movie.domain.model.cinema.Theaters
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.presentation.model.TheatersUiModel
import woowacourse.movie.presentation.model.toUiModel
import java.time.LocalDateTime

class MoviesPresenter(
    private val view: MoviesContract.View,
    private val theaters: Theaters,
    private val movies: List<Movie>,
) : MoviesContract.Presenter {
    override fun fetchData() {
        view.showScreen(movies.map { it.toUiModel() })
    }

    override fun availableTheatersAndCount(movieId: Int) {
        val theaterSchedules = theaters.findTheatersByMovieId(movieId, LocalDateTime.now())
        val movie = movies.find { it.id == movieId }?.toUiModel() ?: return

        view.showAvailableTheatersAndCount(movie, TheatersUiModel(theaterSchedules))
    }
}
