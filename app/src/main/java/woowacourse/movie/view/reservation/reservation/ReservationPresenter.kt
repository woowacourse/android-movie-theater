package woowacourse.movie.view.reservation.reservation

import woowacourse.movie.model.MovieDao
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.model.MovieTime
import woowacourse.movie.model.ReservationState
import woowacourse.movie.model.TheaterUIModel
import woowacourse.movie.model.TicketCount
import woowacourse.movie.view.ReservationUiFormatter
import java.time.LocalDate
import java.time.LocalDateTime

class ReservationPresenter(
    val view: ReservationContract.View,
) : ReservationContract.Presenter {
    private lateinit var reservationState: ReservationState
    private var currentTimeTable: List<Int> = emptyList()
    private val movieDao by lazy { MovieDao() }
    var isTimeSelected = false

    override fun fetchData(getMovie: () -> TheaterUIModel?) {
        val theaterUIModel = getMovie()
        if (theaterUIModel == null) {
            view.showErrorDialog()
            return
        }

        reservationState =
            ReservationState(
                movie = theaterUIModel.movie,
                movieDate = MovieDate(theaterUIModel.movie.startDate, theaterUIModel.movie.endDate),
                movieTime = MovieTime(),
                ticketCount = TicketCount(),
                theaterName = theaterUIModel.name,
            )

        updateMovieInfo()
    }

    override fun initDateAdapter() {
        var duration = reservationState.movieDate.getDateTable(LocalDate.now())
        if (duration.isEmpty()) duration = listOf(reservationState.movie.startDate)

        view.updateDateAdapter(duration, 0)
        selectDate(duration[0])
    }

    override fun selectDate(date: LocalDate) {
        val now = LocalDateTime.now()
        val screenTimes =
            movieDao.getScreenTimes(reservationState.theaterName, reservationState.movie.title)
        currentTimeTable = movieDao.getTimeTable(now, date, screenTimes)
        if (currentTimeTable.isEmpty()) {
            isTimeSelected = false
        }
        reservationState.movieDate.updateDate(date)
        updateReservationState(movieDate = reservationState.movieDate)
        view.updateTimeAdapter(
            currentTimeTable.map {
                ReservationUiFormatter.movieTimeToUI(it)
            },
        )
    }

    override fun selectTime(position: Int) {
        reservationState.movieTime.updateTime(currentTimeTable[position])
        updateReservationState(movieTime = reservationState.movieTime)
    }

    override fun plusTicketCount() {
        updateReservationState(
            ticketCount = reservationState.ticketCount.plus(1),
        )
        view.showTicketCount(reservationState.ticketCount.value)
    }

    override fun minusTicketCount() {
        updateReservationState(
            ticketCount = reservationState.ticketCount.minus(1),
        )
        view.showTicketCount(reservationState.ticketCount.value)
    }

    override fun createTicket(onCreated: (MovieTicket) -> Unit) {
        val ticket =
            MovieTicket(
                title = reservationState.movie.title,
                date = reservationState.movieDate.value,
                time = ReservationUiFormatter.movieTimeToUI(reservationState.movieTime.value),
                count = reservationState.ticketCount.value,
                theaterName = reservationState.theaterName,
            )
        onCreated(ticket)
    }

    fun restoreTicketCount(count: Int) {
        updateReservationState(
            ticketCount = TicketCount(count),
        )
        view.showTicketCount(reservationState.ticketCount.value)
    }

    fun currentTicketCount(): Int = reservationState.ticketCount.value

    private fun updateMovieInfo() {
        val movie = reservationState.movie
        view.showMovieInfo(
            posterResId = movie.poster,
            title = movie.title,
            startDate = ReservationUiFormatter.localDateToUI(movie.startDate),
            endDate = ReservationUiFormatter.localDateToUI(movie.endDate),
            runningTime = movie.runningTime,
        )
    }

    private fun updateReservationState(
        movieDate: MovieDate = reservationState.movieDate,
        movieTime: MovieTime = reservationState.movieTime,
        ticketCount: TicketCount = reservationState.ticketCount,
    ) {
        reservationState =
            reservationState.copy(
                movieDate = movieDate,
                movieTime = movieTime,
                ticketCount = ticketCount,
            )
    }
}
