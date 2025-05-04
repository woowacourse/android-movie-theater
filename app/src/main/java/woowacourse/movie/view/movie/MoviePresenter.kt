package woowacourse.movie.view.movie

import woowacourse.movie.R
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.toUiModel
import woowacourse.movie.view.model.AdUiModel
import woowacourse.movie.view.model.MovieFixture.movies
import woowacourse.movie.view.model.MovieFixture.screenings
import woowacourse.movie.view.model.MovieListItem
import woowacourse.movie.view.model.MovieUiModel
import java.time.LocalDateTime

class MoviePresenter(
    val view: MovieContract.View,
) : MovieContract.Presenter {
    override fun fetchMovies() {
        val movies = getShowingMovies().map { it.toUiModel() }
        val items = generateMovieListWithAds(movies)
        view.showMovies(items)
    }

    override fun reservationSelected(movie: MovieUiModel) {
        view.showTheaterInfo(movie)
    }

    private fun generateMovieListWithAds(movies: List<MovieUiModel>): List<MovieListItem> =
        movies.chunked(MOVIE_COUNT).flatMap { chunk ->
            chunk + AdUiModel(AD_NAME, R.drawable.advertisement)
        }

    private fun getShowingMovies(): List<Movie> {
        val now = LocalDateTime.now()
        val today = now.toLocalDate()
        val currentHour = now.hour

        val result = mutableSetOf<Movie>()
        getTheaterNames().forEach { theaterName ->
            getMovies(theaterName).forEach { movie ->
                val screenTimes = getScreenTimes(theaterName, movie.title)
                if (today == movie.endDate) {
                    if (screenTimes.any { time -> time > currentHour }) {
                        result.add(movie)
                    }
                } else if (!today.isBefore(movie.startDate) && !today.isAfter(movie.endDate)) {
                    result.add(movie)
                }
            }
        }

        return result.toList()
    }

    private fun getTheaterNames(): List<String> = screenings.keys.toList()

    private fun getMovies(theaterName: String): List<Movie> {
        val movieNames = screenings[theaterName]?.keys ?: return emptyList()
        return movieNames.mapNotNull { movieName -> movies[movieName] }
    }

    private fun getScreenTimes(
        theaterName: String,
        movieName: String,
    ): List<Int> = getTimeSlot(theaterName)[movieName] ?: emptyList()

    private fun getTimeSlot(theaterName: String): Map<String, List<Int>> = screenings[theaterName] ?: emptyMap()

    companion object {
        private const val MOVIE_COUNT = 3
        private const val AD_NAME = "광고"
    }
}
