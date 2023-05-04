package woowacourse.movie.presentation.booking

import woowacourse.movie.domain.model.rules.ScreeningTimes
import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.domain.model.tools.TicketCount
import java.time.LocalDate
import java.time.LocalTime

class BookingPresenter(
    override val view: BookingContract.View,
    private var ticketCount: TicketCount = TicketCount(),
    private val movie: Movie
) : BookingContract.Presenter {

    init {
        view.setTicketCount(ticketCount.value)
    }

    override fun plusTicketCount() {
        ticketCount = ticketCount.plus()
        view.setTicketCount(ticketCount.value)
    }

    override fun minusTicketCount() {
        ticketCount = ticketCount.minus()
        view.setTicketCount(ticketCount.value)
    }

    override fun getTicketCurrentCount(): Int = ticketCount.value

    override fun getScreeningTimes(date: LocalDate): List<LocalTime> {
        val times = ScreeningTimes.getScreeningTime(date)
        updateTimeSpinner(times)
        return times
    }

    private fun updateTimeSpinner(times: List<LocalTime>) {
        view.setTimeSpinnerItems(times)
    }

    override fun getScreeningDates(): List<LocalDate> {
        val dates = movie.getScreeningDates()
        updateDateSpinner(dates)
        return dates
    }

    private fun updateDateSpinner(dates: List<LocalDate>) {
        view.setDateSpinnerItems(dates)
    }
}
