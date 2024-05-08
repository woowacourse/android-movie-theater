package woowacourse.movie.moviedetail

import woowacourse.movie.moviedetail.uimodel.BookingDetailUiModel
import woowacourse.movie.moviedetail.uimodel.BookingInfoUiModel
import woowacourse.movie.moviedetail.uimodel.HeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.MovieDetailUiModel
import woowacourse.movie.moviedetail.uimodel.ScheduleUiModels

interface MovieDetailContract {
    interface View {
        fun intendToPlusCount()

        fun intendToMinusCount()

        fun showMovieInfo(reservation: MovieDetailUiModel)

        fun showCantDecreaseError(minCount: Int)

        fun showBookingDetail(
            screeningDateTimeUiModels: ScheduleUiModels,
            bookingDetailUiModel: BookingDetailUiModel,
        )

        fun updateHeadCount(updatedCount: HeadCountUiModel)

        fun showScreeningMovieError()

        fun showMovieReservationError()

        fun showTime(times: List<String>)

        fun navigateToSeatSelect(bookingInfoUiModel: BookingInfoUiModel)
    }

    interface Presenter {
        fun loadMovieDetail(screeningId: Long)

        fun plusCount()

        fun minusCount()

        fun selectDate(position: Int)

        fun selectTime(position: Int)

        fun confirm()
    }
}
