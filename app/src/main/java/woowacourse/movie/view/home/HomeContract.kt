package woowacourse.movie.view.home

import woowacourse.movie.view.home.movies.MovieUi

interface HomeContract {
    interface Presenter {
        fun fetchData()
    }

    interface View {
        fun showMoviesScreen(movieUis: List<MovieUi>)
    }
}
