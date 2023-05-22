package woowacourse.movie.feature.bottomseat

import woowacourse.movie.feature.common.itemModel.TheaterItemModel
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.model.TheaterScreeningInfoState

interface TheaterContract {
    interface View {
        fun setTheaterItems(theaters: List<TheaterItemModel>)
        fun selectTheater(theater: SelectTheaterAndMovieState)
        fun bottomSheetDismiss()
    }

    interface Presenter {
        fun loadTheatersData(movie: MovieState)
        fun clickTheater(theater: TheaterScreeningInfoState, movie: MovieState)
    }
}
