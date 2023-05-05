package woowacourse.movie.ui.completed

import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.main.MovieMapper.toUiModel
import woowacourse.movie.movie.MovieRepository
import woowacourse.movie.theater.TheaterRepository

class CompletedPresenter(
    private val view: CompletedContract.View,
    private val reservation: ReservationUiModel
) : CompletedContract.Presenter {

    override fun initReservation() {
        val movie = MovieRepository.getMovie(reservation.movieId).toUiModel()
        val theaterName = TheaterRepository.getTheater(reservation.theaterId).name

        view.initView(
            movie = movie,
            theaterName = theaterName
        )
    }
}
