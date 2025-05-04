package woowacourse.movie.view.reservation.detail

import woowacourse.movie.R
import woowacourse.movie.domain.model.MovieDao
import woowacourse.movie.domain.model.MovieTicket
import woowacourse.movie.domain.model.ReservationUiModel
import woowacourse.movie.domain.model.TheaterUIModel
import woowacourse.movie.domain.model.toReservationUiModel
import woowacourse.movie.view.ReservationUiFormatter
import java.time.LocalDate
import java.time.LocalDateTime

class ReservationDetailPresenter(
    val view: ReservationDetailContract.View,
) : ReservationDetailContract.Presenter {
    private lateinit var reservationUIModel: ReservationUiModel
    private var currentTimeTable: List<Int> = emptyList()
    private val movieDao by lazy { MovieDao() }
    private var isTimeSelected = false

    override fun fetchData(theater: TheaterUIModel?) {
        if (theater == null) {
            view.showErrorDialog()
            return
        }

        reservationUIModel = theater.toReservationUiModel()

        view.showMovieInfo(theater.movie)
    }

    override fun initDateAdapter() {
        var duration = reservationUIModel.movieDate.getDateTable(LocalDate.now())
        if (duration.isEmpty()) duration = listOf(reservationUIModel.movieDate.value)

        view.updateDateAdapter(duration, 0)
        selectDate(duration[0])
    }

    override fun selectDate(date: LocalDate) {
        val now = LocalDateTime.now()
        val screenTimes =
            movieDao.getScreenTimes(reservationUIModel.theaterName, reservationUIModel.title)
        currentTimeTable = movieDao.getTimeTable(now, date, screenTimes)
        if (currentTimeTable.isEmpty()) {
            isTimeSelected = false
        }

        reservationUIModel = reservationUIModel.copy(movieDate = reservationUIModel.movieDate)

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

    private fun createTicket(): MovieTicket =
        MovieTicket(
            title = reservationUIModel.title,
            date = reservationUIModel.movieDate.value,
            time = reservationUIModel.movieTime,
            count = reservationUIModel.ticketCount,
            theaterName = reservationUIModel.theaterName,
        )
}
