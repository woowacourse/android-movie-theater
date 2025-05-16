package woowacourse.movie.presentation.view.reservation.detail

import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import java.time.LocalDate
import java.time.LocalDateTime

interface ReservationDetailContract {
    interface View {
        fun showMovieInfo(movie: MovieUiModel)

        fun showErrorDialog()

        fun showTicketCount(count: Int)

        fun updateDateAdapter(
            duration: List<LocalDate>,
            selected: Int,
        )

        fun updateTimeAdapter(
            date: LocalDate,
            times: List<String>,
        )

        fun navigateToSeatSelect(reservationInfo: ReservationInfoUiModel)

        fun showToast(stringResId: Int)

        fun showTimeNotSelectedError()
    }

    interface Presenter {
        fun fetchData(
            movie: MovieUiModel?,
            theater: TheaterUiModel?,
        )

        fun initDateAdapter(movie: MovieUiModel)

        fun selectDate(date: LocalDate)

        fun selectTime(
            date: LocalDate,
            position: Int,
        )

        fun plusTicketCount()

        fun minusTicketCount()

        fun completeSelected(selectedDateTime: LocalDateTime?)
    }
}
