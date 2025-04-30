package woowacourse.movie.booking.detail

import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Scheduler
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Ticket
import woowacourse.movie.movie.MovieUiModel
import woowacourse.movie.movie.TheaterUiModel
import java.time.LocalDate
import java.time.LocalTime

class BookingDetailPresenter(
    private val view: BookingDetailContract.View,
    private val movie: MovieUiModel,
    private val theater: TheaterUiModel,
) : BookingDetailContract.Presenter {
    private lateinit var ticket: Ticket
    private lateinit var scheduler: Scheduler

    override fun initializeData() {
        scheduler = Scheduler(movie.toDomain(), theater.schedule.screeningTimes)

        view.showMovieInfo(movie)
        view.showTicket(ticket.toUiModel())
        view.showScreeningDates(
            dates = scheduler.screeningPeriods(),
            selected = ticket.selectedDate,
        )
        view.showScreeningTimes(
            times = scheduler.screeningTimes(ticket.selectedDate),
            selected = ticket.selectedTime,
        )
    }

    override fun selectDate(date: LocalDate) {
        ticket = ticket.updateDate(date)
        val times = scheduler.screeningTimes(date)

        if (times.isEmpty()) {
            val nextDate = date.plusDays(1)
            ticket = ticket.updateDate(nextDate)
            val nextTimes = scheduler.screeningTimes(nextDate)
            nextTimes.firstOrNull()?.let {
                ticket = ticket.updateTime(it)
            }
            view.showScreeningTimes(nextTimes, ticket.selectedTime)
        } else {
            ticket = ticket.updateTime(times.first())
            view.showScreeningTimes(times, ticket.selectedTime)
        }

        view.showTicket(ticket.toUiModel())
    }

    override fun selectTime(time: LocalTime) {
        if (ticket.selectedTime == time) return

        ticket = ticket.updateTime(time)
        view.showScreeningTimes(scheduler.screeningTimes(ticket.selectedDate), ticket.selectedTime)
        view.showTicket(ticket.toUiModel())
    }

    override fun increaseHeadCount() {
        ticket = ticket.plusHeadCount()
        view.showTicket(ticket.toUiModel())
    }

    override fun decreaseHeadCount() {
        if (ticket.isHeadCountValid()) ticket = ticket.minusHeadCount()
        view.showTicket(ticket.toUiModel())
    }

    override fun confirmReservation() {
        if (ticket.isHeadCountValid()) {
            view.startSeatSelectionActivity(ticket.toUiModel())
        }
    }

    override fun getCurrentTicketUiModel(): TicketUiModel {
        return ticket.toUiModel()
    }

    override fun restoreTicketData(
        headCount: Int,
        screeningDate: String?,
        screeningTime: String?,
    ) {
        ticket =
            Ticket(
                title = movie.title,
                headCount = HeadCount(headCount),
                selectedDate = screeningDate?.let { LocalDate.parse(it) } ?: LocalDate.now(),
                selectedTime = screeningTime?.let { LocalTime.parse(it) } ?: LocalTime.now(),
                seats = Seats(emptyList()),
            )
    }

    override fun createDefaultTicket() {
        ticket =
            Ticket(
                title = movie.title,
                headCount = HeadCount(0),
                selectedDate = LocalDate.now(),
                selectedTime = LocalTime.now(),
                seats = Seats(emptyList()),
            )
    }
}
