package woowacourse.movie.moviereservation

import woowacourse.movie.moviereservation.uimodel.BookingDetailUiModel
import woowacourse.movie.moviereservation.uimodel.HeadCountUiModel
import woowacourse.movie.moviereservation.uimodel.MovieReservationUiModel
import woowacourse.movie.moviereservation.uimodel.ScreeningDateTimesUiModel

interface MovieReservationContract {
    interface View {
        fun intendToPlusCount()

        fun intendToMinusCount()

        fun showMovieInfo(reservation: MovieReservationUiModel)

        fun showCantDecreaseError(minCount: Int)

        fun showBookingDetail(
            screeningDateTimeUiModels: ScreeningDateTimesUiModel,
            bookingDetailUiModel: BookingDetailUiModel,
        )

        fun updateHeadCount(updatedCount: HeadCountUiModel)

        fun navigateToReservationResultView(reservationId: Long)

        fun showScreeningMovieError()

        fun showMovieReservationError()
    }

    interface Presenter {
        fun loadMovieDetail(screenMovieId: Long)

        fun plusCount()

        fun minusCount()
    }
}
