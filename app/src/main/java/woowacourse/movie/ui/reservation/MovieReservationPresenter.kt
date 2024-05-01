package woowacourse.movie.ui.reservation

import woowacourse.movie.model.data.MovieContents
import woowacourse.movie.model.data.Theaters
import woowacourse.movie.model.data.UserTickets
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
    private val movieContents: MovieContents,
    private val theaters: Theaters,
    private val userTickets: UserTickets,
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
            movieContent = movieContents.find(movieContentId)
            theater = theaters.find(theaterId)
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
                movieContent.title,
                theater.name,
                LocalDateTime.of(screeningDate.date, movieTime),
                ReservationDetail(reservationCount.count),
            )
        val ticketId = userTickets.save(userTicket)
        view.moveMovieSeatSelectionPage(ticketId)
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
