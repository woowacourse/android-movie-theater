package woowacourse.movie.presentation.booking

import woowacourse.movie.data.movie.DefaultMovieData
import woowacourse.movie.data.movie.MovieData
import woowacourse.movie.domain.model.tools.TicketCount
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.CinemaModel
import woowacourse.movie.presentation.model.ReservationModel
import java.time.LocalDateTime

class BookingPresenter(
    private val movieData: MovieData,
    private val view: BookingContract.View,
) : BookingContract.Presenter {

    private var ticketCount: TicketCount = TicketCount()

    private fun findMovieById(movieId: Long) = movieData.findMovieById(movieId)
    override fun setMovieInfo(movieId: Long) {
        val movie = findMovieById(movieId) ?: run {
            view.setMovieInfo(DefaultMovieData.defaultMovie)
            return
        }
        view.setMovieInfo(movie.toPresentation())
    }

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

    override fun reserveMovie(
        cinemaModel: CinemaModel,
        localDateTime: LocalDateTime,
    ) {
        val reservation = ReservationModel(
            cinemaModel.movieId,
            cinemaModel.cinemaName,
            localDateTime,
            ticketCount.value,
        )
        view.reservationMovie(reservation)
    }

    override fun initMovieDates(movieId: Long) {
        val movie = findMovieById(movieId)

        if (movie != null) {
            view.setScreeningDates(movie.getScreeningDates())
        }
    }
}
