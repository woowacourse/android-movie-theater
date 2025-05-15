package woowacourse.movie.presentation.booking

import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.scheduler.DefaultScheduler
import woowacourse.movie.domain.model.scheduler.Scheduler
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter(
    private val view: BookingContract.View,
    screening: Screening,
    private val scheduler: Scheduler = DefaultScheduler(screening),
) : BookingContract.Presenter {
    private var _ticket = Ticket(screening.movie, screening.theater)
    val ticket: Ticket get() = _ticket

    override fun loadBooking() {
        view.showMovie(ticket.movie)
        updateHeadCount()
        view.showBookableDates(scheduler.getBookableDates(), ticket.showtime.toLocalDate())
    }

    override fun selectScreeningDate(date: LocalDate) {
        _ticket = ticket.copy(showtime = LocalDateTime.of(date, ticket.showtime.toLocalTime()))
        view.showBookableTimes(
            scheduler.getBookableTimes(date),
            ticket.showtime.toLocalTime(),
        )
    }

    override fun selectScreeningTime(time: LocalTime) {
        _ticket = ticket.copy(showtime = LocalDateTime.of(ticket.showtime.toLocalDate(), time))
    }

    override fun increaseHeadCount() {
        _ticket = ticket.copy(headCount = ticket.headCount + 1)
        updateHeadCount()
    }

    override fun decreaseHeadCount() {
        _ticket = ticket.copy(headCount = ticket.headCount - 1)
        updateHeadCount()
    }

    override fun confirmBooking() {
        view.navigateToSeatSelect(ticket)
    }

    override fun restoreTicket(ticket: Ticket) {
        _ticket = ticket
        loadBooking()
    }

    private fun updateHeadCount() {
        view.showHeadCount(ticket.headCount.value)
        view.updateDecreaseButtonState(ticket.headCount.isMinimum().not())
        view.updateIncreaseButtonState(ticket.headCount.isMaximum().not())
    }
}
