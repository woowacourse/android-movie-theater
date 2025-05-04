package woowacourse.movie.presentation.home.movies.dialog

import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.common.model.TheaterUiModel
import woowacourse.movie.presentation.common.model.TheatersUiModel

interface TheaterBottomSheetDialogContract {
    interface Presenter {
        fun fetch(
            theaters: TheatersUiModel,
            movie: MovieUiModel,
        )

        fun presentTheaterItem(theater: TheaterUiModel)
    }

    interface View {
        fun showTheaters(theaters: TheatersUiModel)

        fun showDetail(
            movie: MovieUiModel,
            theater: TheaterUiModel,
        )
    }
}
