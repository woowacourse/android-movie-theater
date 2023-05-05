package woowacourse.movie.feature.movieList

import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.TheaterMovieState

interface MovieListContract {
    interface View {
        fun navigateMovieDetail(theaterMovie: TheaterMovieState)
        fun showBottomSheetDialog(movie: MovieState)
        fun navigateAdbDetail(adv: AdvState)
        fun updateItems(items: List<ItemModel>)
    }

    interface Presenter {
        fun loadMovieAndAdvItemList()
        fun receiveTheaterInfo(theaterMovie: TheaterMovieState)
    }
}
