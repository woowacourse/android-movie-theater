package woowacourse.movie.feature.movielist

import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SelectTheaterAndMovieState

interface MovieListContract {
    interface View {
        fun navigateMovieDetail(selectTheaterMovie: SelectTheaterAndMovieState)
        fun showTheaterBottomSheet(movie: MovieState)
        fun navigateAdbDetail(adv: AdvState)
        fun updateItems(items: List<ItemModel>)
    }

    interface Presenter {
        fun loadMovieAndAdvItems()
        fun receiveTheaterInfo(theaterMovie: SelectTheaterAndMovieState)
    }
}
