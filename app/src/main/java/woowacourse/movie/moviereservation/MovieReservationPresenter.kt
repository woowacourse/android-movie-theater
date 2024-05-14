package woowacourse.movie.moviereservation

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.ScreeningMovie
import woowacourse.movie.moviereservation.uimodel.BookingInfo
import woowacourse.movie.moviereservation.uimodel.CurrentBookingDetail
import woowacourse.movie.repository.MovieRepository
import java.time.LocalDateTime
import kotlin.concurrent.thread

class MovieReservationPresenter(
    private val view: MovieReservationContract.View,
    private val repository: MovieRepository,
) : MovieReservationContract.Presenter {
    private lateinit var screeningMovie: ScreeningMovie

    override fun loadMovieDetail(screenMovieId: Long) {
        thread {
            runCatching {
                screeningMovie = repository.screenMovieById(screenMovieId)
            }.onSuccess {
                view.showMovieInfo(screeningMovie.toMovieReservationUiModel())
                view.showBookingDetail(
                    screeningMovie.toScreeningDateTimeUiModel(),
                    CurrentBookingDetail(
                        HeadCount.MIN_COUNT,
                    ),
                )
            }.onFailure {
                view.showScreeningMovieError()
            }
        }.join()
    }

    override fun plusCount(currentCount: Int) {
        val count = HeadCount(currentCount).increase()
        view.updateHeadCount(count.count)
    }

    override fun minusCount(currentCount: Int) {
        val count = HeadCount(currentCount)
        runCatching {
            count.decrease()
        }.onSuccess { updatedCount ->
            view.updateHeadCount(updatedCount.count)
        }.onFailure {
            view.showCantDecreaseError(HeadCount.MIN_COUNT)
        }
    }

    override fun completeBookingDetail(
        movieId: Long,
        theaterId: Long,
        currentBookingDetail: CurrentBookingDetail,
    ) {
        thread {
            val localDate = screeningMovie.screenDateTimes[currentBookingDetail.datePosition].date
            val localTime =
                screeningMovie.screenDateTimes[currentBookingDetail.datePosition].times[currentBookingDetail.timePosition]

            val bookingInfo =
                BookingInfo(
                    movieId,
                    theaterId,
                    currentBookingDetail.count,
                    LocalDateTime.of(localDate, localTime),
                )

            view.navigateToSelectSeatView(bookingInfo)
        }.join()
    }
}
