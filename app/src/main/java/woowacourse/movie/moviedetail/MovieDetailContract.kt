package woowacourse.movie.moviedetail

import woowacourse.movie.moviedetail.uimodel.BookingDetailUiModel
import woowacourse.movie.moviedetail.uimodel.HeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.ReservationPlanUiModel
import woowacourse.movie.moviedetail.uimodel.ScreeningDateTimesUiModel

interface MovieDetailContract {
    interface View {
        fun intendToPlusCount()

        fun intendToMinusCount()

        fun showMovieInfo(reservation: ReservationPlanUiModel)

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
