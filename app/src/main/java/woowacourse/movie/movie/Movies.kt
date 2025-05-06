package woowacourse.movie.movie

import woowacourse.movie.domain.Movie
import java.time.LocalDate

interface Movies {
    interface View {
        fun showMovies(movies: List<Movie>)

        fun navigateToBook(movie: Movie)

        fun navigateToAdPage()

        fun showError(messageResId: Int)
    }

    interface Presenter {
        fun loadMovies()

        fun selectedMovie(movie: Movie, targetDate: LocalDate)

        fun selectedAd()
    }
}
