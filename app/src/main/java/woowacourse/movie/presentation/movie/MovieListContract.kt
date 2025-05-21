package woowacourse.movie.presentation.movie

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.presentation.movie.adapter.MovieListItem

interface MovieListContract {
    interface View {
        fun showMovieList(items: List<MovieListItem>)

        fun showTheaterList(movie: Movie)
    }

    interface Presenter {
        fun loadMovieList()

        fun selectMovie(movie: Movie)
    }
}
