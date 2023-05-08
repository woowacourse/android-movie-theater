package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.view.model.MovieUiModel

interface MovieListContract {
    interface View {
        fun showMovieList(movies: List<MovieUiModel>)
    }
    interface Presenter {
        fun fetchMovieList()
    }
}
