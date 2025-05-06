package woowacourse.movie.movie

import woowacourse.movie.R
import woowacourse.movie.domain.Movie
import java.time.LocalDate

class MoviesPresenter(
    private val view: Movies.View,
) : Movies.Presenter {
    override fun loadMovies() {
        val movies = woowacourse.movie.domain.Movies.value.toList()
        view.showMovies(movies)
    }

    override fun selectedMovie(movie: Movie, targetDate: LocalDate) {
        if (movie.screeningPeriod.isEnd(targetDate)) {
            view.showError(R.string.error_over_end_date)
            return
        }
        view.navigateToBook(movie)
    }

    override fun selectedAd() {
        view.navigateToAdPage()
    }
}
