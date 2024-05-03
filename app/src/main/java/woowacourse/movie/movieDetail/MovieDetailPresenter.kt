package woowacourse.movie.movieDetail

import woowacourse.movie.model.movieInfo.MovieInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
) : MovieDetailContract.Presenter {
    private var ticketNum = 1

    private lateinit var movie: MovieInfo

    override fun load(movie: MovieInfo) {
        this.movie = movie
    }

    override fun onTicketPlusClicked() {
        if (ticketNum < 10) {
            ticketNum++
            view.onTicketCountChanged()
        } else {
            view.showToast("최대 티켓 수량에 도달했습니다.")
        }
    }

    override fun onTicketMinusClicked() {
        if (ticketNum > 1) {
            ticketNum--
            view.onTicketCountChanged()
        } else {
            view.showToast("최소 티켓 수량입니다.")
        }
    }

    override fun updateTimeSpinner(times: List<String>) {
        view.updateTimeAdapter(times)
    }

    override fun generateDateRange() {
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
}
