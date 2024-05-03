package woowacourse.movie.moviereservation

import woowacourse.movie.model.HeadCount
import woowacourse.movie.moviereservation.uimodel.BookingDetailUiModel
import woowacourse.movie.repository.MovieRepository

class MovieReservationPresenter(
    private val view: MovieReservationContract.View,
    private val repository: MovieRepository,
) : MovieReservationContract.Presenter {
    private var count = HeadCount(1)

    override fun loadMovieDetail(screenMovieId: Long) {
        runCatching {
            repository.screenMovieById(screenMovieId)
        }.onSuccess { screeningMovie ->
            view.showMovieInfo(screeningMovie.toMovieReservationUiModel())
            view.showBookingDetail(
                screeningMovie.toScreeningDateTimeUiModel(),
                BookingDetailUiModel(
                    HeadCount.MIN_COUNT,
                    screeningMovie.startDate,
                    screeningMovie.screenDateTimes.first().times.first(),
                ),
            )
        }.onFailure {
            view.showScreeningMovieError()
        }
    }

    override fun plusCount() {
        count = count.increase()
        view.updateHeadCount(count.toHeadCountUiModel())
    }

    override fun minusCount() {
        runCatching {
            count = count.decrease()
        }.onSuccess {
            view.updateHeadCount(count.toHeadCountUiModel())
        }
    }
}
