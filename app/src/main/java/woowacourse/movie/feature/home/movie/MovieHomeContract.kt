package woowacourse.movie.feature.home.movie

import woowacourse.movie.model.Movie
import woowacourse.movie.util.BasePresenter

interface MovieHomeContract {
    interface View {
        fun displayMovies(movies: List<Movie>)

        fun displayTheaterSelectionDialog(movieId: Long)
    }

    interface Presenter : BasePresenter {
        fun loadMovies()
    }
}
