package woowacourse.movie.contract

import woowacourse.movie.data.MovieListItemViewData
import woowacourse.movie.data.MovieListItemsViewData
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.TheatersViewData

interface MovieAdapterContract {
    interface View {
        val presenter: Presenter
        fun onClickItem(movieViewData: MovieViewData, theatersViewData: TheatersViewData)
        fun setAdapterData(movies: MovieListItemsViewData)
    }

    interface Presenter {
        val view: View
        fun setMovieList()
        fun makeTheaterDialog(movie: MovieListItemViewData)
    }
}
