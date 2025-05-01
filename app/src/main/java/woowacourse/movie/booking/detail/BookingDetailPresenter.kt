package woowacourse.movie.booking.detail

import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Scheduler
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Ticket
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel
import woowacourse.movie.ui.model.TicketUiModel
import woowacourse.movie.util.Formatter.formatStringDateDotSeparated
import woowacourse.movie.util.Formatter.formatStringTimeWithMidnight24
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
        view.showHeadCount()
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
            view.showScreeningTimes(theater.schedule.screeningTimes, ticket.selectedTime)
        } else {
            ticket = ticket.updateTime(times.first())
            view.showScreeningTimes(times, ticket.selectedTime)
        }
    }

    override fun selectTime(time: LocalTime) {
        if (ticket.selectedTime == time) return

        ticket = ticket.updateTime(time)
        view.showScreeningTimes(scheduler.screeningTimes(ticket.selectedDate), ticket.selectedTime)
    }

    override fun increaseHeadCount() {
        ticket = ticket.plusHeadCount()
    }

    override fun decreaseHeadCount() {
        if (ticket.isHeadCountValid()) ticket = ticket.minusHeadCount()
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
                theater = theater.place,
                title = movie.title,
                headCount = HeadCount(headCount),
                selectedDate = screeningDate?.let { formatStringDateDotSeparated(it) } ?: LocalDate.now(),
                selectedTime = screeningTime?.let { formatStringTimeWithMidnight24(it) } ?: LocalTime.now(),
                seats = Seats(emptyList()),
            )
    }

    override fun createDefaultTicket() {
        ticket =
            Ticket(
                theater = theater.place,
                title = movie.title,
                headCount = HeadCount(0),
                selectedDate = LocalDate.now(),
                selectedTime = LocalTime.now(),
                seats = Seats(emptyList()),
            )
    }
}
