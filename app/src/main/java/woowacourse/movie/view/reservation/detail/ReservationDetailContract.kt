package woowacourse.movie.view.reservation.detail

import woowacourse.movie.domain.model.MovieTicket
import woowacourse.movie.domain.model.TheaterUIModel
import woowacourse.movie.view.model.MovieUiModel
import java.time.LocalDate

interface ReservationDetailContract {
    interface View {
        fun showMovieInfo(movie: MovieUiModel)

        fun showErrorDialog()

        fun showTicketCount(count: Int)

        fun updateDateAdapter(
            duration: List<LocalDate>,
            selected: Int,
        )

        fun updateTimeAdapter(times: List<String>)

        fun navigateToSeatSelect(ticket: MovieTicket)

        fun showToast(stringResId: Int)

        fun showTimeNotSelectedError()
    }

    interface Presenter {
        fun fetchData(theater: TheaterUIModel?)

        fun initDateAdapter(movie: MovieUiModel)

        fun selectDate(date: LocalDate)

        fun selectTime(position: Int)

        fun plusTicketCount()

        fun minusTicketCount()

        fun completeSelected()
    }
}
