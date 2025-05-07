package woowacourse.movie.presentation.view.theater

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import woowacourse.movie.presentation.model.TheatersUiModel
import woowacourse.movie.presentation.model.toDomain
import woowacourse.movie.presentation.model.toPresentation

class TheaterPresenter(
    val view: TheaterContract.View,
) : TheaterContract.Presenter {
    private lateinit var movie: Movie

    override fun fetchTheaters(
        movie: MovieUiModel,
        theaters: TheatersUiModel,
    ) {
        this.movie = movie.toDomain()

        view.showTheaters(theaters.value)
    }

    override fun theaterSelected(theater: TheaterUiModel) {
        if (theater.totalScreeningTimes == 0) {
            view.showEmptySlotMessage()
        } else {
            view.navigateToReservation(movie.toPresentation(), theater)
        }
    }
}
