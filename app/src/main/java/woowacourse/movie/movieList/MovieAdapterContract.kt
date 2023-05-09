package woowacourse.movie.movieList

import woowacourse.movie.common.model.MovieListItemViewData
import woowacourse.movie.common.model.MovieListItemsViewData
import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.common.model.TheatersViewData

interface MovieAdapterContract {
    interface View {
        val presenter: Presenter
        fun onClickItem(movieViewData: MovieViewData, theatersViewData: TheatersViewData)
        fun setAdapterData(movies: MovieListItemsViewData)
    }

    interface Presenter {
        val view: View
        fun makeTheaterDialog(movie: MovieListItemViewData)
    }
}
