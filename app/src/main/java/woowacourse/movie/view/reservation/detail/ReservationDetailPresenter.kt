package woowacourse.movie.view.reservation.detail

import woowacourse.movie.R
import woowacourse.movie.domain.model.MovieDao
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.MovieTicket
import woowacourse.movie.domain.model.MovieTime
import woowacourse.movie.domain.model.ReservationUIModel
import woowacourse.movie.domain.model.TheaterUIModel
import woowacourse.movie.domain.model.TicketCount
import woowacourse.movie.view.ReservationUiFormatter
import woowacourse.movie.view.model.toLocalDate
import java.time.LocalDate
import java.time.LocalDateTime

class ReservationDetailPresenter(
    val view: ReservationDetailContract.View,
) : ReservationDetailContract.Presenter {
    lateinit var reservationUIModel: ReservationUIModel
    private var currentTimeTable: List<Int> = emptyList()
    private val movieDao by lazy { MovieDao() }
    var isTimeSelected = false

    override fun fetchData(getMovie: () -> TheaterUIModel?) {
        val theaterUIModel = getMovie()
        if (theaterUIModel == null) {
            view.showErrorDialog()
            return
        }

        reservationUIModel =
            ReservationUIModel(
                movie = theaterUIModel.movie,
                movieDate =
                    MovieDate(
                        theaterUIModel.movie.startDate.toLocalDate(),
                        theaterUIModel.movie.endDate.toLocalDate(),
                    ),
                movieTime = MovieTime(),
                ticketCount = TicketCount().value,
                theaterName = theaterUIModel.name,
            )

        updateMovieInfo()
    }

    override fun initDateAdapter() {
        var duration = reservationUIModel.movieDate.getDateTable(LocalDate.now())
        if (duration.isEmpty()) duration = listOf(reservationUIModel.movie.startDate.toLocalDate())

        view.updateDateAdapter(duration, 0)
        selectDate(duration[0])
    }

    override fun selectDate(date: LocalDate) {
        val now = LocalDateTime.now()
        val screenTimes =
            movieDao.getScreenTimes(reservationUIModel.theaterName, reservationUIModel.movie.name)
        currentTimeTable = movieDao.getTimeTable(now, date, screenTimes)
        if (currentTimeTable.isEmpty()) {
            isTimeSelected = false
        }
        reservationUIModel.movieDate.updateDate(date)
        updateReservationState(movieDate = reservationUIModel.movieDate)
        view.updateTimeAdapter(
            currentTimeTable.map {
                ReservationUiFormatter.movieTimeToUI(it)
            },
        )
    }

    override fun selectTime(position: Int) {
        reservationUIModel.movieTime.updateTime(currentTimeTable[position])
        updateReservationState(movieTime = reservationUIModel.movieTime)
    }

    override fun plusTicketCount() {
        updateReservationState(
            ticketCount = TicketCount(reservationUIModel.ticketCount + 1),
        )
        view.showTicketCount(reservationUIModel.ticketCount)
    }

    override fun minusTicketCount() {
        if (reservationUIModel.ticketCount == 1) {
            view.showToast(R.string.reservation_info_minimum_ticket_count)
            return
        }
        updateReservationState(
            ticketCount = TicketCount(reservationUIModel.ticketCount - 1),
        )
        view.showTicketCount(reservationUIModel.ticketCount)
    }

    override fun createTicket(onCreated: (MovieTicket) -> Unit) {
        val ticket =
            MovieTicket(
                title = reservationUIModel.movie.name,
                date = reservationUIModel.movieDate.value,
                time = ReservationUiFormatter.movieTimeToUI(reservationUIModel.movieTime.value),
                count = reservationUIModel.ticketCount,
                theaterName = reservationUIModel.theaterName,
            )
        onCreated(ticket)
    }

    fun restoreTicketCount(count: Int) {
        updateReservationState(
            ticketCount = TicketCount(count),
        )
        view.showTicketCount(reservationUIModel.ticketCount)
    }

    fun currentTicketCount(): Int = reservationUIModel.ticketCount

    private fun updateMovieInfo() {
        view.showMovieInfo(reservationUIModel.movie)
    }

    private fun updateReservationState(
        movieDate: MovieDate = reservationUIModel.movieDate,
        movieTime: MovieTime = reservationUIModel.movieTime,
        ticketCount: TicketCount = TicketCount(reservationUIModel.ticketCount),
    ) {
        reservationUIModel =
            reservationUIModel.copy(
                movieDate = movieDate,
                movieTime = movieTime,
                ticketCount = ticketCount.value,
            )
    }
}
