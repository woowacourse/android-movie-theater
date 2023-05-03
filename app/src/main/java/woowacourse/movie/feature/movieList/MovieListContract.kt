package woowacourse.movie.feature.movieList

import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

interface MovieListContract {
    interface View {
        fun navigateMovieDetail(movie: MovieState)
        fun navigateAdbDetail(adv: AdvState)
        fun updateItems(items: List<ItemModel>)
    }

    interface Presenter {
        fun loadMovieAndAdvItemList()
    }
}
