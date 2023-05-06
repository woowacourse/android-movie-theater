package woowacourse.movie.feature.movielist

import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

interface MovieListContract {
    interface View {
        fun setItems(items: List<ItemModel>)
        fun navigateMovieDetail(movie: MovieState)
        fun navigateAdvDetail(adv: AdvState)
    }

    interface Presenter {
        fun setListItems()
    }
}
