package woowacourse.movie.presentation.movies

import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.presentation.movies.adapter.MovieListItem

interface MoviesContract {
    interface View {
        fun showMovies(movieListItems: List<MovieListItem>)

        fun showTheaterSelectDialog(movie: Movie)
    }

    interface Presenter {
        fun onViewCreated()

        fun onMovieClicked(movie: Movie)
    }
}
