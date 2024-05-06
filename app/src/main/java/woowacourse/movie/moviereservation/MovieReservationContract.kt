package woowacourse.movie.moviereservation

import woowacourse.movie.moviereservation.uimodel.BookingInfo
import woowacourse.movie.moviereservation.uimodel.CurrentBookingDetail
import woowacourse.movie.moviereservation.uimodel.MovieReservationUiModel
import woowacourse.movie.moviereservation.uimodel.ScreeningDateTimesUiModel

interface MovieReservationContract {
    interface View {
        fun showMovieInfo(reservation: MovieReservationUiModel)

        fun showCantDecreaseError(minCount: Int)

        fun showBookingDetail(
            screeningDateTimeUiModels: ScreeningDateTimesUiModel,
            currentBookingDetail: CurrentBookingDetail,
        )

        fun updateHeadCount(updatedCount: Int)

        fun navigateToSelectSeatView(bookingInfo: BookingInfo)

        fun showScreeningMovieError()

        fun showMovieReservationError()
    }

    interface Presenter {
        fun loadMovieDetail(screenMovieId: Long)

        fun plusCount(currentCount: Int)

        fun minusCount(currentCount: Int)

        fun completeBookingDetail(
            movieId: Long,
            theaterId: Long,
            currentBookingDetail: CurrentBookingDetail,
        )
    }
}
