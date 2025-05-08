package woowacourse.movie.movie

import woowacourse.movie.R
import woowacourse.movie.data.MovieFactory
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Movies
import java.time.LocalDate

class MoviesPresenter(
    private val view: MovieContract.View,
) : MovieContract.Presenter {
    override fun loadMovies() {
        val items = MovieFactory(Movies.value.toList()).items
        view.showMovies(items)
    }

    override fun selectedMovie(movie: Movie) {
        if (movie.screeningPeriod.isEnd(LocalDate.now())) {
            view.showError(R.string.error_over_end_date)
            return
        }
        view.navigateToBook(movie)
    }

    override fun selectedAd() {
        view.navigateToAdPage()
    }
}
