package woowacourse.movie.moviedetail

import woowacourse.movie.model.HeadCount
import woowacourse.movie.moviedetail.uimodel.BookingDetailUiModel
import woowacourse.movie.moviedetail.uimodel.toHeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.toMovieReservationUiModel
import woowacourse.movie.moviedetail.uimodel.toScreeningDateTimeUiModel
import woowacourse.movie.repository.MovieRepository

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val repository: MovieRepository,
) : MovieDetailContract.Presenter {
    private var count = HeadCount(1)

    override fun loadMovieDetail(screenMovieId: Long) {
        runCatching {
            repository.screeningById(screenMovieId)
        }.onSuccess { screeningMovie ->
            view.showMovieInfo(screeningMovie.toMovieReservationUiModel())
            view.showBookingDetail(
                screeningMovie.toScreeningDateTimeUiModel(),
                BookingDetailUiModel(
                    HeadCount.MIN_COUNT,
                    screeningMovie.startDate,
                    screeningMovie.schedules.first().times.first(),
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
