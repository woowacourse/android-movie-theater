package woowacourse.movie.presentation.view.theater

import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import woowacourse.movie.presentation.model.TheatersUiModel

interface TheaterContract {
    interface View {
        fun showTheaters(theaters: List<TheaterUiModel>)

        fun showEmptySlotMessage()

        fun navigateToReservation(
            movie: MovieUiModel,
            theater: TheaterUiModel,
        )
    }

    interface Presenter {
        fun fetchTheaters(
            movie: MovieUiModel,
            theaters: TheatersUiModel,
        )

        fun theaterSelected(theater: TheaterUiModel)
    }
}
