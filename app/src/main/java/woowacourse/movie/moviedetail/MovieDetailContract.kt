package woowacourse.movie.moviedetail

import woowacourse.movie.moviedetail.uimodel.BookingDetailUiModel
import woowacourse.movie.moviedetail.uimodel.HeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.MovieDetailUiModel
import woowacourse.movie.moviedetail.uimodel.ScreeningDateTimesUiModel

interface MovieDetailContract {
    interface View {
        fun intendToPlusCount()

        fun intendToMinusCount()

        fun showMovieInfo(reservation: MovieDetailUiModel)

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
        fun loadMovieDetail(screeningId: Long)

        fun plusCount()

        fun minusCount()
    }
}
