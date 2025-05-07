package woowacourse.movie.view.movies

import woowacourse.movie.domain.model.MovieListItem

interface MoviesContract {
    interface View {
        fun showMovies(movies: List<MovieListItem>)
    }

    interface Presenter {
        fun loadData()
    }
}
