package woowacourse.movie.ui.reservation

import woowacourse.movie.model.data.DefaultMovieDataSource
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.model.movie.ReservationCount
import woowacourse.movie.model.movie.ReservationDetail
import woowacourse.movie.model.movie.ScreeningDate
import woowacourse.movie.model.movie.Theater
import woowacourse.movie.model.movie.UserTicket
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieReservationPresenter(
    private val view: MovieReservationContract.View,
    private val movieContentDataSource: DefaultMovieDataSource<Long, MovieContent>,
    private val theaterDataSource: DefaultMovieDataSource<Long, Theater>,
    private val userTicketDataSource: DefaultMovieDataSource<Long, UserTicket>,
) :
    MovieReservationContract.Presenter {
    private lateinit var reservationCount: ReservationCount
    private lateinit var screeningDate: ScreeningDate
    private lateinit var movieTime: LocalTime
    lateinit var theater: Theater
    lateinit var movieContent: MovieContent

    override fun updateReservationCount(count: Int) {
        reservationCount = ReservationCount(count)
        view.updateReservationCount(count)
    }

    override fun selectDate(date: LocalDate) {
        screeningDate = ScreeningDate(date)
    }

    override fun selectTime(time: LocalTime) {
        movieTime = time
    }

    override fun loadMovieContent(
        movieContentId: Long,
        theaterId: Long,
    ) {
        try {
            movieContent = movieContentDataSource.find(movieContentId)
            theater = theaterDataSource.find(theaterId)
        } catch (e: NoSuchElementException) {
            view.showError(e)
        }
    }

    override fun decreaseCount() {
        reservationCount--
        view.updateReservationCount(reservationCount.count)
    }

    override fun increaseCount() {
        reservationCount++
        view.updateReservationCount(reservationCount.count)
    }

    override fun reserveSeat() {
        val userTicket =
            UserTicket(
                title = movieContent.title,
                theater = theater.name,
                screeningStartDateTime = LocalDateTime.of(screeningDate.date, movieTime),
                reservationDetail = ReservationDetail(reservationCount.count),
            )
        val ticketId = userTicketDataSource.save(userTicket)
        view.moveMovieSeatSelectionPage(ticketId)
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
