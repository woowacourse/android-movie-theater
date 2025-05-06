package woowacourse.movie.presentation.home.movies

import woowacourse.movie.R
import woowacourse.movie.domain.model.cinema.Theaters
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.movie.MovieContent
import woowacourse.movie.domain.model.rule.AdInsertionPolicy
import woowacourse.movie.domain.model.rule.MovieAdInsertionPolicy
import woowacourse.movie.presentation.common.fixture.createDummyMovies
import woowacourse.movie.presentation.common.fixture.dummyTheaters
import woowacourse.movie.presentation.common.model.TheatersUiModel
import woowacourse.movie.presentation.common.model.toUiModel
import woowacourse.movie.presentation.home.movies.adapter.item.toUiModel
import java.time.LocalDateTime

class MoviesPresenter(
    private val view: MoviesContract.View,
    private val movies: List<Movie> = createDummyMovies(100),
    private val theaters: Theaters = dummyTheaters,
    private val adInsertionPolicy: AdInsertionPolicy<MovieContent, MovieContent.MovieAd> = MovieAdInsertionPolicy(),
) : MoviesContract.Presenter {
    override fun fetchData() {
        val movieContents = movies.toMovieContent().map { it.toUiModel() }
        view.showScreen(movieContents)
    }

    override fun availableTheatersAndCount(movieId: Int) {
        val theaterSchedules = theaters.findTheatersByMovieId(movieId, LocalDateTime.now())
        val movie = movies.find { it.id == movieId }?.toUiModel() ?: return

        view.showAvailableTheatersAndCount(movie, TheatersUiModel(theaterSchedules))
    }

    private fun List<Movie>.toMovieContent(): List<MovieContent> {
        val entries = this.map { MovieContent.MovieEntry(it) }
        return adInsertionPolicy.insert(entries) {
            MovieContent.MovieAd(R.drawable.woowacourse_ad)
        }
    }
}
