package woowacourse.movie.ui.home

import woowacourse.movie.uimodel.MovieListModel

interface HomeContract {
    interface View {
        val presenter: Presenter

        fun setMovieList(movies: List<MovieListModel>)
    }

    interface Presenter
}
