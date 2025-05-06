package woowacourse.movie.theater

import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

interface TheaterBottomSheetContract {
    interface View {
        fun setUpTheaterList(theaters: List<TheaterUiModel>)

        fun startBookingDetail(
            movie: MovieUiModel,
            theater: TheaterUiModel,
        )
    }

    interface Presenter {
        fun initializeInfo(
            movie: MovieUiModel,
            theaters: List<TheaterUiModel>,
        )

        fun selectTheater(theater: TheaterUiModel)
    }
}
