package woowacourse.movie.view.reservation.detail

import woowacourse.movie.R
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.domain.model.TicketCount
import woowacourse.movie.view.ReservationUiFormatter
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TheaterUiModel
import woowacourse.movie.view.model.toDomain
import woowacourse.movie.view.model.toPresentation
import java.time.LocalDate
import java.time.LocalDateTime

class ReservationDetailPresenter(
    val view: ReservationDetailContract.View,
    private val dummyTheaters: Theaters,
) : ReservationDetailContract.Presenter {
    private lateinit var movie: Movie
    private lateinit var theaterName: String
    private var ticketCount: TicketCount = TicketCount()
    private var currentTimeTable: List<Int> = emptyList()

    override fun fetchData(
        movie: MovieUiModel?,
        theater: TheaterUiModel?,
    ) {
        if (movie == null || theater == null) {
            view.showErrorDialog()
            return
        }

        this.movie = movie.toDomain()
        this.theaterName = theater.name

        view.showMovieInfo(movie)
    }

    override fun initDateAdapter(movie: MovieUiModel) {
        val now = LocalDate.now()
        var duration =
            MovieDate(movie.date.startDate, movie.date.endDate).getDateTable(now)
        if (duration.isEmpty()) duration = listOf(now)

        view.updateDateAdapter(duration, 0)
        selectDate(duration[0])
    }

    override fun selectDate(date: LocalDate) {
        val now = LocalDateTime.now()

        val theater = dummyTheaters.value.find { it.name == theaterName }
        currentTimeTable = theater?.getTimesOnDate(movie, date).orEmpty()

        view.updateTimeAdapter(
            date,
            currentTimeTable.map {
                ReservationUiFormatter.movieTimeToUI(it)
            },
        )
    }

    override fun selectTime(
        date: LocalDate,
        position: Int,
    ) {
        val selectedTime = ReservationUiFormatter.movieTimeToLocalTime(currentTimeTable[position])
    }

    override fun plusTicketCount() {
        ticketCount += 1
        view.showTicketCount(ticketCount.value)
    }

    override fun minusTicketCount() {
        if (ticketCount.value == 1) {
            view.showToast(R.string.reservation_info_minimum_ticket_count)
            return
        }

        ticketCount -= 1
        view.showTicketCount(ticketCount.value)
    }

    override fun completeSelected(selectedDateTime: LocalDateTime?) {
        if (selectedDateTime == null) {
            view.showTimeNotSelectedError()
            return
        }

        val reservationInfo =
            ReservationInfo(
                movie.title,
                selectedDateTime,
                Seats.create(),
                ticketCount,
            ).toPresentation(theaterName)

        view.navigateToSeatSelect(reservationInfo)
    }

    fun restoreTicketCount(count: Int) {
        ticketCount = TicketCount(count)
        view.showTicketCount(ticketCount.value)
    }

    fun currentTicketCount(): Int = ticketCount.value
}
