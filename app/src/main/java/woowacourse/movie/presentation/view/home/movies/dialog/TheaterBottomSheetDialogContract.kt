package woowacourse.movie.presentation.view.home.movies.dialog

import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import woowacourse.movie.presentation.model.TheatersUiModel

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
