package woowacourse.movie.view.reservation.detail

import androidx.annotation.StringRes
import woowacourse.movie.model.reservation.MovieTicket
import woowacourse.movie.model.theater.TheaterUIModel
import java.time.LocalDate

interface ReservationDetailContract {
    interface View {
        fun showMovieInfo(
            posterResId: Int,
            title: String,
            startDate: String,
            endDate: String,
            runningTime: Int,
        )

        fun showErrorMessage(
            @StringRes messageResId: Int,
        )

        fun showTicketCount(count: Int)

        fun updateDateAdapter(
            duration: List<LocalDate>,
            selected: Int,
        )

        fun updateTimeAdapter(times: List<String>)

        fun navigateToSeatSelect(ticket: MovieTicket)

        fun showInfoMessage(stringResId: Int)

        fun finishView()
    }

    interface Presenter {
        fun fetchData(getMovie: () -> TheaterUIModel?)

        fun initDateAdapter()

        fun selectDate(date: LocalDate)

        fun selectTime(position: Int)

        fun plusTicketCount()

        fun minusTicketCount()

        fun createTicket(onCreated: (MovieTicket) -> Unit)
    }
}
