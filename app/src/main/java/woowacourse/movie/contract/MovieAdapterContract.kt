package woowacourse.movie.contract

import woowacourse.movie.data.MovieListItemsViewData

interface MovieAdapterContract {
    interface View {
        val presenter: Presenter
        fun setAdapterData(movies: MovieListItemsViewData)
    }

    interface Presenter {
        val view: View
        fun setMovieList()
    }
}
