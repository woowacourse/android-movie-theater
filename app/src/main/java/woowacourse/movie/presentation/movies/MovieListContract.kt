package woowacourse.movie.presentation.movies

import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.presentation.movies.adapter.MovieListItem

interface MovieListContract {
    interface View {
        fun showMovieList(movieListItems: List<MovieListItem>)

        fun showTheaterList(movie: Movie)
    }

    interface Presenter {
        fun loadMovieList()

        fun onMovieClicked(movie: Movie)
    }
}
