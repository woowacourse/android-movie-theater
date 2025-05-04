package woowacourse.movie.view.reservation.detail

import woowacourse.movie.R
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.view.ReservationUiFormatter
import woowacourse.movie.view.model.MovieFixture.screenings
import woowacourse.movie.view.model.MovieTicket
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.model.TheaterUIModel
import woowacourse.movie.view.model.toLocalDate
import woowacourse.movie.view.model.toReservationUiModel
import java.time.LocalDate
import java.time.LocalDateTime

class ReservationDetailPresenter(
    val view: ReservationDetailContract.View,
) : ReservationDetailContract.Presenter {
    private lateinit var reservationUIModel: ReservationUiModel
    private var currentTimeTable: List<Int> = emptyList()
    private var isTimeSelected = false

    override fun fetchData(theater: TheaterUIModel?) {
        if (theater == null) {
            view.showErrorDialog()
            return
        }

        reservationUIModel = theater.toReservationUiModel()

        view.showMovieInfo(theater.movie)
    }

    override fun initDateAdapter(movie: MovieUiModel) {
        val now = LocalDate.now()
        var duration =
            MovieDate(movie.startDate.toLocalDate(), movie.endDate.toLocalDate()).getDateTable(now)
        if (duration.isEmpty()) duration = listOf(now)

        view.updateDateAdapter(duration, 0)
        selectDate(duration[0])
    }

    override fun selectDate(date: LocalDate) {
        val now = LocalDateTime.now()
        val screenTimes = getScreenTimes(reservationUIModel.theaterName, reservationUIModel.title)
        currentTimeTable = getTimeTable(now, date, screenTimes)
        if (currentTimeTable.isEmpty()) {
            isTimeSelected = false
        }

        reservationUIModel =
            reservationUIModel.copy(movieDate = ReservationUiFormatter.localDateToUI(date))

        view.updateTimeAdapter(
            currentTimeTable.map {
                ReservationUiFormatter.movieTimeToUI(it)
            },
        )
    }

    override fun selectTime(position: Int) {
        val selectedTime = currentTimeTable.getOrNull(position) ?: return
        reservationUIModel =
            reservationUIModel.copy(movieTime = ReservationUiFormatter.movieTimeToUI(selectedTime))
        isTimeSelected = true
    }

    override fun plusTicketCount() {
        reservationUIModel =
            reservationUIModel.copy(ticketCount = reservationUIModel.ticketCount + 1)
        view.showTicketCount(reservationUIModel.ticketCount)
    }

    override fun minusTicketCount() {
        if (reservationUIModel.ticketCount == 1) {
            view.showToast(R.string.reservation_info_minimum_ticket_count)
            return
        }
        reservationUIModel =
            reservationUIModel.copy(ticketCount = reservationUIModel.ticketCount - 1)
        view.showTicketCount(reservationUIModel.ticketCount)
    }

    override fun completeSelected() {
        if (!isTimeSelected) {
            view.showTimeNotSelectedError()
            return
        }

        val ticket = createTicket()
        view.navigateToSeatSelect(ticket)
    }

    fun restoreTicketCount(count: Int) {
        reservationUIModel = reservationUIModel.copy(ticketCount = count)
        view.showTicketCount(reservationUIModel.ticketCount)
    }

    fun currentTicketCount(): Int = reservationUIModel.ticketCount

    private fun getTimeTable(
        now: LocalDateTime,
        selectedDate: LocalDate,
        screenTimes: List<Int>,
    ): List<Int> {
        if (now.toLocalDate() == selectedDate) {
            return screenTimes.timeTable(now.hour)
        }
        return screenTimes
    }

    private fun getScreenTimes(
        theaterName: String,
        movieName: String,
    ): List<Int> = getTimeSlot(theaterName)[movieName] ?: emptyList()

    private fun getTimeSlot(theaterName: String): Map<String, List<Int>> = screenings[theaterName] ?: emptyMap()

    private fun createTicket(): MovieTicket =
        MovieTicket(
            title = reservationUIModel.title,
            date = reservationUIModel.movieDate.toLocalDate(),
            time = reservationUIModel.movieTime,
            count = reservationUIModel.ticketCount,
            theaterName = reservationUIModel.theaterName,
        )
}

private fun List<Int>.timeTable(nowHour: Int): List<Int> {
    forEachIndexed { index, time ->
        if (time > nowHour) {
            return slice(index..<size)
        }
    }
    return emptyList()
}
