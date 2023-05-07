package woowacourse.movie.feature.movieList

import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SelectTheaterAndMovieState

interface MovieListContract {
    interface View {
        fun navigateMovieDetail(selectTheaterMovie: SelectTheaterAndMovieState)
        fun showBottomSheetDialog(movie: MovieState)
        fun navigateAdbDetail(adv: AdvState)
        fun updateItems(items: List<CommonItemModel>)
        fun errorLoadData()
    }

    interface Presenter {
        fun loadMovieAndAdvItemList()
        fun receiveTheaterInfo(theaterMovie: SelectTheaterAndMovieState)
    }
}
