package woowacourse.movie.presentation.booking

import woowacourse.movie.data.MovieData
import woowacourse.movie.domain.model.rules.ScreeningTimes
import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.domain.model.tools.TicketCount
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.ReservationModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter(
    private val view: BookingContract.View,
) : BookingContract.Presenter {

    private var movie: Movie = MovieData.findMovieById(view.movieId)
    private var ticketCount: TicketCount = TicketCount()

    override fun requireMovieModel(id: Long): MovieModel = movie.toPresentation()

    override fun setTicketCount(count: Int) {
        ticketCount = TicketCount(count)
        setTicketCountView()
    }

    override fun addTicket() {
        ticketCount = ticketCount.plus()
        setTicketCountView()
    }

    override fun subTicket() {
        ticketCount = ticketCount.minus()
        setTicketCountView()
    }

    private fun setTicketCountView() {
        view.setTicketCount(ticketCount.value)
    }

    override fun reserveMovie(localDateTime: LocalDateTime): ReservationModel =
        ReservationModel(movie.id, localDateTime, ticketCount.value)

    override fun getScreeningDate(): List<LocalDate> = movie.getScreeningDates()

    override fun getScreeningTime(position: Int): List<LocalTime> =
        ScreeningTimes.getScreeningTime(getScreeningDate()[position])
}
