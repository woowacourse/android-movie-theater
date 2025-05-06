package woowacourse.movie.presentation.home.movies

import woowacourse.movie.R
import woowacourse.movie.domain.model.cinema.Theaters
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.rule.AdFrequency
import woowacourse.movie.presentation.common.fixture.createDummyMovies
import woowacourse.movie.presentation.common.fixture.dummyTheaters
import woowacourse.movie.presentation.common.model.TheatersUiModel
import woowacourse.movie.presentation.common.model.toUiModel
import woowacourse.movie.presentation.common.util.AdInjector
import woowacourse.movie.presentation.home.movies.adapter.item.AdItem
import woowacourse.movie.presentation.home.movies.adapter.item.MovieItem
import woowacourse.movie.presentation.home.movies.adapter.item.MovieMainItem
import java.time.LocalDateTime

class MoviesPresenter(
    private val view: MoviesContract.View,
    private val adFrequency: AdFrequency = AdFrequency(3),
) : MoviesContract.Presenter {
    private var movies: List<Movie> = listOf()
    private val theaters: Theaters = dummyTheaters

    override fun fetchData() {
        movies = createDummyMovies(10_000).map { it }
        view.showScreen(injectAds(movies))
    }

    override fun availableTheatersAndCount(movieId: Int) {
        val theaterSchedules = theaters.findTheatersByMovieId(movieId, LocalDateTime.now())
        val movie = movies.find { it.id == movieId }?.toUiModel() ?: return

        view.showAvailableTheatersAndCount(movie, TheatersUiModel(theaterSchedules))
    }

    private fun injectAds(movies: List<Movie>): List<MovieMainItem> {
        val injector =
            AdInjector<MovieMainItem>(adFrequency.value) {
                AdItem(R.drawable.woowacourse_ad)
            }

        return injector.inject(movies.map { MovieItem(it.toUiModel()) })
    }
}
