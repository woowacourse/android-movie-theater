package woowacourse.movie.presentation.movies

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.presentation.movies.adapter.MovieListItem

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
