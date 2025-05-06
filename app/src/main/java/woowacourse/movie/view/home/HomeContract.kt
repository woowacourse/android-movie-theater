package woowacourse.movie.view.home

import woowacourse.movie.view.home.movies.MovieItem

interface HomeContract {
    interface Presenter {
        fun fetchData()
    }

    interface View {
        fun showMoviesScreen(movieItems: List<MovieItem>)
    }
}
