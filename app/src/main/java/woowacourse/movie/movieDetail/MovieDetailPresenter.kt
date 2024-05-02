package woowacourse.movie.movieDetail

import woowacourse.movie.model.movieInfo.MovieInfo
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val movie: MovieInfo?,
) : MovieDetailContract.Presenter {
    private var ticketNum = 1

    override fun load() {
        movie?.let { view.initializeViews(it) }
    }

    override fun onTicketPlusClicked() {
        if (ticketNum < 10) {
            ticketNum++
            view.onTicketCountChanged(ticketNum)
        } else {
            view.showToast("최대 티켓 수량에 도달했습니다.")
        }
    }

    override fun onTicketMinusClicked() {
        if (ticketNum > 1) {
            ticketNum--
            view.onTicketCountChanged(ticketNum)
        } else {
            view.showToast("최소 티켓 수량입니다.")
        }
    }

    override fun getTicketNum(): Int {
        return ticketNum
    }

    override fun updateTimeSpinner(times: List<String>) {
        view.updateTimeAdapter(times)
    }

    override fun generateDateRange(
        startDate: LocalDate,
        endDate: LocalDate,
    ) {
        val dates = mutableListOf<String>()
        var date = startDate
        while (!date.isAfter(endDate)) {
            dates.add(date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
            date = date.plusDays(1)
        }
        view.updateDateAdapter(dates)
    }
}
