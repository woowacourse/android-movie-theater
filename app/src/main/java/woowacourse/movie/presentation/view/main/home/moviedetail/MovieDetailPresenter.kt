package woowacourse.movie.presentation.view.main.home.moviedetail

import com.example.domain.MovieSchedule
import woowacourse.movie.presentation.model.Movie
import woowacourse.movie.presentation.model.MovieBookingInfo

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val movie: Movie?
) :
    MovieDetailContract.Presenter {
    private val movieSchedule =
        MovieSchedule(movie!!.startDate, movie.endDate)

    override fun onCreate() {
        if (movie == null) {
            view.finishErrorView()
            return
        }
        view.initView(movie)
    }

    override fun getMovieSchedule() {
        val scheduleDate = movieSchedule.getScheduleDates()
        val scheduleTime = movieSchedule.getScheduleTimes(scheduleDate[0])
        view.updateScheduleDateView(scheduleDate, scheduleTime)
    }

    override fun addTicket(currentTicketCount: Int) {
        view.updateTicketCountView(currentTicketCount + 1)
    }

    override fun removeTicket(currentTicketCount: Int) {
        if (currentTicketCount.toString() == BASE_TICKET_COUNT_CHARACTER) {
            view.showErrorTicketCountIsZeroView()
        }
        view.updateTicketCountView(currentTicketCount - 1)
    }

    override fun getMovieBookingInfo(ticketCount: Int, movieDate: String, movieTime: String) {
        val movieBookingInfo = MovieBookingInfo(
            movie!!,
            movieDate,
            movieTime,
            ticketCount
        )
        view.showInfoSelectedView(movieBookingInfo)
    }

    override fun getMovieScheduleTime(date: String) {
        val movieScheduleTime = movieSchedule.getScheduleTimes(date)
        view.updateScheduleTimeView(movieScheduleTime)
    }

    companion object {
        private const val BASE_TICKET_COUNT_CHARACTER = "1"
    }
}