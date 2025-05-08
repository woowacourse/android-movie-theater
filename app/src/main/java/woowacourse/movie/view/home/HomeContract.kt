package woowacourse.movie.view.home

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.MovieListItem

interface HomeContract {
    interface Presenter {
        fun loadMovies()
    }

    interface View {
        fun showMovies(movieListItems: List<MovieListItem>)

        fun showTheaterSelectDialog(movie: Movie)
    }
}
