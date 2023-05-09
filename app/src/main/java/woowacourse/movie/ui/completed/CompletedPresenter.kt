package woowacourse.movie.ui.completed

import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.main.MainModelRepository
import woowacourse.movie.model.main.MovieMapper.toUiModel

class CompletedPresenter(
    private val view: CompletedContract.View,
    private val repository: MainModelRepository,
    private val reservation: ReservationUiModel
) : CompletedContract.Presenter {

    override fun initReservation() {
        val movie = repository.findMovieById(reservation.movieId).toUiModel()
        val theaterName = repository.findTheaterById(reservation.theaterId).name

        view.initView(
            movie = movie,
            theaterName = theaterName
        )
    }
}
