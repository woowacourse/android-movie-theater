package woowacourse.movie.ui.booking

import woowacourse.movie.model.BookedMovie
import woowacourse.movie.model.main.MovieMapper.toUiModel
import woowacourse.movie.movie.Movie
import woowacourse.movie.movie.MovieRepository
import woowacourse.movie.theater.Theater
import woowacourse.movie.theater.TheaterRepository
import woowacourse.movie.ticket.TicketCount
import java.time.LocalDateTime

class BookingPresenter(
    private val view: BookingContract.View,
    movieId: Long,
    theaterId: Long
) : BookingContract.Presenter {

    override val movie: Movie = MovieRepository.getMovie(movieId)
    override val theater: Theater = TheaterRepository.getTheater(theaterId)
    override var ticketCount: TicketCount = TicketCount()

    override fun initMovie() {
        view.initView(movie.toUiModel())
    }

    override fun initTicketCount() {
        view.setTicketCountText(ticketCount.value)
    }

    override fun minusTicketCount() {
        ticketCount = ticketCount.minus()

        view.setTicketCountText(ticketCount.value)
    }

    override fun plusTicketCount() {
        ticketCount = ticketCount.plus()

        view.setTicketCountText(ticketCount.value)
    }

    override fun initDateTimes() {
        view.setDates(movie.screeningDates)
        view.setTimes(theater.screeningTimes)
    }

    override fun createBookedMovie(dateTime: LocalDateTime): BookedMovie {

        return BookedMovie(
            movieId = movie.id,
            theaterId = 0,
            ticketCount = ticketCount.value,
            bookedDateTime = dateTime
        )
    }
}
