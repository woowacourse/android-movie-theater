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

    override fun onTicketPlusClicked(currentTicketNum: Int) {
        if (currentTicketNum < 10) {
            ticketNum = currentTicketNum + 1
            view.onTicketCountChanged(ticketNum)
        } else {
            view.showToast("최대 티켓 수량에 도달했습니다.")
        }
    }

    override fun onTicketMinusClicked(currentTicketNum: Int) {
        if (currentTicketNum > 1) {
            ticketNum = currentTicketNum - 1
            view.onTicketCountChanged(ticketNum)
        } else {
            view.showToast("최소 티켓 수량입니다.")
        }
    }

    override fun getTicketNum(): Int {
        return ticketNum
    }

    override fun updateTimeSpinner(date: String) {
        val times = mutableListOf<String>()
        val startHour = if (isWeekend(date)) 9 else 10
        var time = startHour
        while (time <= 24) {
            times.add("$time:00")
            time += 2
        }
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

    private fun isWeekend(date: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val parsedDate = LocalDate.parse(date, formatter)
        return parsedDate.dayOfWeek == DayOfWeek.SATURDAY || parsedDate.dayOfWeek == DayOfWeek.SUNDAY
    }
}
