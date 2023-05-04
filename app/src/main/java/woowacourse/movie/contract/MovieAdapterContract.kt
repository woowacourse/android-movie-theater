package woowacourse.movie.contract

import woowacourse.movie.data.MovieListItemViewData
import woowacourse.movie.data.MovieListItemsViewData
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.TheatersViewData

interface MovieAdapterContract {
    interface View {
        val presenter: Presenter
        val onClickItem: (MovieViewData, TheatersViewData) -> Unit
        fun setAdapterData(movies: MovieListItemsViewData)
    }

    interface Presenter {
        val view: View
        fun setMovieList()
        fun onClickItem(movie: MovieListItemViewData)
    }
}
