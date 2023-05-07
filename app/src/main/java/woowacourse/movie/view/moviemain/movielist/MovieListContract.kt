package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.view.model.MovieUiModel

interface MovieListContract {
    interface View {
        var presenter: Presenter
        fun showMovieList(movies: List<MovieUiModel>)
    }
    interface Presenter {
        fun fetchMovieList()
    }
}
