package woowacourse.movie.view.theater

import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TheaterUiModel
import woowacourse.movie.view.model.TheatersUiModel

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
