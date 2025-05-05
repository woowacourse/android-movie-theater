package woowacourse.movie.presentation.booking

import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.scheduler.Scheduler
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter(
    private val view: BookingContract.View,
    screening: Screening,
    private val scheduler: Scheduler,
) : BookingContract.Presenter {
    private var _ticket = Ticket(screening.movie, screening.theater)
    val ticket: Ticket get() = _ticket

    override fun loadBooking() {
        view.showMovie(_ticket.movie)
        updateHeadCount()
        view.showBookableDates(scheduler.getBookableDates(), _ticket.showtime.toLocalDate())
    }

    override fun selectScreeningDate(date: LocalDate) {
        _ticket = _ticket.copy(showtime = LocalDateTime.of(date, _ticket.showtime.toLocalTime()))
        view.showBookableTimes(
            scheduler.getBookableTimes(date),
            _ticket.showtime.toLocalTime(),
        )
    }

    override fun selectScreeningTime(time: LocalTime) {
        _ticket = _ticket.copy(showtime = LocalDateTime.of(_ticket.showtime.toLocalDate(), time))
    }

    override fun increaseHeadCount() {
        _ticket = _ticket.copy(headCount = _ticket.headCount + 1)
        updateHeadCount()
    }

    override fun decreaseHeadCount() {
        _ticket = _ticket.copy(headCount = _ticket.headCount - 1)
        updateHeadCount()
    }

    override fun confirmBooking() {
        view.navigateToSeatSelect(_ticket)
    }

    override fun restoreTicket(ticket: Ticket) {
        _ticket = ticket
        loadBooking()
    }

    private fun updateHeadCount() {
        view.showHeadCount(_ticket.headCount.value)
        view.updateDecreaseButtonState(_ticket.headCount.isMinimum().not())
        view.updateIncreaseButtonState(_ticket.headCount.isMaximum().not())
    }
}
