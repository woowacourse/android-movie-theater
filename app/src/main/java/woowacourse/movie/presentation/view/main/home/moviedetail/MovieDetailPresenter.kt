package woowacourse.movie.presentation.view.main.home.moviedetail

import com.example.domain.MovieSchedule
import woowacourse.movie.presentation.model.Movie
import woowacourse.movie.presentation.model.MovieBookingInfo
import woowacourse.movie.presentation.model.Theater

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val movie: Movie?,
    private val theater: Theater?
) :
    MovieDetailContract.Presenter {
    private val movieSchedule =
        MovieSchedule(movie!!.startDate, movie.endDate)

    override fun onCreate() {
        if (movie == null || theater == null) {
            view.finishErrorView()
            return
        }
        view.initView(movie)
    }

    override fun getMovieSchedule(scheduleTime: List<String>) {
        val scheduleDate = movieSchedule.getScheduleDates()
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
            ticketCount,
            theater!!.name
        )
        view.showInfoSelectedView(movieBookingInfo)
    }

    companion object {
        private const val BASE_TICKET_COUNT_CHARACTER = "1"
    }
}