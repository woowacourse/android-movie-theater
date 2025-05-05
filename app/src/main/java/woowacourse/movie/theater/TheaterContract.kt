package woowacourse.movie.theater

import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

interface TheaterContract {
    interface View {
        fun showTheaters(theaters: List<TheaterUiModel>)

        fun navigateToBookingDetail(
            theater: TheaterUiModel,
            movie: MovieUiModel,
        )
    }

    interface Presenter {
        fun initialize(
            movie: MovieUiModel,
            theaters: ArrayList<TheaterUiModel>,
        )

        fun clickTheater(theater: TheaterUiModel)
    }
}
