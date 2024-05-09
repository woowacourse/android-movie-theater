package woowacourse.movie.movieDetail

import woowacourse.movie.model.Cinema
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val cinema: Cinema,
) : MovieDetailContract.Presenter {
    private var ticketNum = 1

    init {
        view.showTicketCount(ticketNum)
    }

    override fun increaseTicketCount() {
        if (ticketNum < 10) {
            ticketNum++
            view.showTicketCount(ticketNum)
        } else {
            view.showTicketMessage("최대 티켓 수량에 도달했습니다.")
        }
    }

    override fun decreaseTicketCount() {
        if (ticketNum > 1) {
            ticketNum--
            view.showTicketCount(ticketNum)
        } else {
            view.showTicketMessage("최소 티켓 수량입니다.")
        }
    }

    override fun updateRunMovieTimes() {
        view.updateTimeAdapter(cinema.theater.times.map { it.toString() })
    }

    override fun loadRunMovieDateRange() {
        val startDate = LocalDate.of(2024, 4, 1)
        val endDate = LocalDate.of(2024, 4, 30)
        val dates = mutableListOf<String>()
        var date = startDate
        while (!date.isAfter(endDate)) {
            dates.add(date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
            date = date.plusDays(1)
        }
        view.updateDateAdapter(dates)
    }

    override fun loadMovieInfo() {
        val movie = cinema.theater.movie
        view.showTitle(movie.title)
        view.showReleaseDate(movie.releaseDate)
        view.showSynopsis(movie.synopsis)
        view.showRunningTime(movie.runningTime)
    }

    override fun confirmPurchase() {
        view.navigateToPurchaseConfirmation(cinema)
    }
}
