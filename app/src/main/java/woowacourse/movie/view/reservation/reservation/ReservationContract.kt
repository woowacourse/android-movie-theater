package woowacourse.movie.view.reservation.reservation

import woowacourse.movie.model.MovieTicket
import woowacourse.movie.model.TheaterUIModel
import java.time.LocalDate

interface ReservationContract {
    interface View {
        fun showMovieInfo(
            posterResId: Int,
            title: String,
            startDate: String,
            endDate: String,
            runningTime: Int,
        )

        fun showErrorDialog()

        fun showTicketCount(count: Int)

        fun updateDateAdapter(
            duration: List<LocalDate>,
            selected: Int,
        )

        fun updateTimeAdapter(times: List<String>)

        fun navigateToSeatSelect(ticket: MovieTicket)

        fun showToast(stringResId: Int)
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
