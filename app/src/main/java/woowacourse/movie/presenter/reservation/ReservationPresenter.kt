package woowacourse.movie.presenter.reservation

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.MovieDate
import woowacourse.movie.model.movie.MovieTime
import woowacourse.movie.model.movie.MovieToReserve
import woowacourse.movie.model.theater.MovieScreeningInfoByTheater
import woowacourse.movie.model.ticket.TicketCount
import java.time.LocalDate
import java.time.LocalDateTime

class ReservationPresenter(
    private val view: ReservationContract.View,
) : ReservationContract.Presenter {
    private lateinit var movieScreeningInfoByTheater: MovieScreeningInfoByTheater
    private var ticketCount = TicketCount()
    private val movieDate by lazy {
        MovieDate(
            movieScreeningInfoByTheater.movie.startDate, movieScreeningInfoByTheater.movie.endDate
        )
    }
    private val movieTime by lazy { MovieTime() }

    override fun updateMovieData(movieScreeningInfoByTheater: MovieScreeningInfoByTheater) {
        this.movieScreeningInfoByTheater = movieScreeningInfoByTheater
        updateView(movieScreeningInfoByTheater.movie)
    }

    private fun updateView(movie: Movie) {
        view.showTitle(movie.title)
        view.showPoster(movie.poster)
        view.showRunningTime(movie.runningTime)
        view.showScreeningDate(movie.startDate, movie.endDate)
        view.showTicketCount(ticketCount.value)
        view.setupDateAdapter(movieDate.getDateTable(LocalDate.now()))
        view.updateTimes(movieTime.getTimeTable(LocalDateTime.now(), movieDate.value))
    }

    override fun increaseTicketCount() {
        ticketCount += 1
        view.showTicketCount(ticketCount.value)
    }

    override fun decreaseTicketCount() {
        runCatching {
            ticketCount -= 1
        }.onSuccess {
            view.showTicketCount(ticketCount.value)
        }.onFailure { error ->
            view.showErrorToastMessage(error.message.toString())
        }
    }

    override fun onMovieToReserveRequest() {
        val movieToReserve = MovieToReserve(
            id = movieScreeningInfoByTheater.movie.id,
            title = movieScreeningInfoByTheater.movie.title,
            movieDate = movieDate,
            movieTime = movieTime,
            ticketCount = ticketCount,
        )
        view.showSeatSelectionView(movieToReserve)
    }

    override fun updateMovieDate(date: LocalDate) {
        movieDate.updateDate(date)
        view.updateTimes(movieTime.getTimeTable(LocalDateTime.now(), movieDate.value))
    }

    override fun updateMovieTime(time: Int) {
        movieTime.updateTime(time)
    }

    override fun updateTicketCount(count: Int?) {
        if (count != null) {
            ticketCount += count - 1
        }
        view.showTicketCount(ticketCount.value)
    }

    override fun updateSelectedDatePosition(position: Int) {
        view.showSelectedDate(position)
    }

    override fun updateSelectedTimePosition(position: Int) {
        view.showSelectedTime(position)
    }
}
