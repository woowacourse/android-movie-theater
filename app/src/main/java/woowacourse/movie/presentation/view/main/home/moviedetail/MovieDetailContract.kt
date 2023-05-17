package woowacourse.movie.presentation.view.main.home.moviedetail

import woowacourse.movie.presentation.model.Movie
import woowacourse.movie.presentation.model.MovieBookingInfo

interface MovieDetailContract {
    interface View {
        fun initView(movie: Movie)
        fun finishErrorView()
        fun updateScheduleDateView(scheduleDate: List<String>, scheduleTime: List<String>)
        fun updateTicketCountView(currentTicketCount: Int)
        fun showErrorTicketCountIsZeroView()
        fun showInfoSelectedView(movieBookingInfo: MovieBookingInfo)
        fun updateScheduleTimeView(movieScheduleTime: List<String>)
    }

    interface Presenter {
        fun initPresenter()
        fun getMovieSchedule(scheduleTime: List<String>)
        fun addTicket(currentTicketCount: Int)
        fun removeTicket(currentTicketCount: Int)
        fun getMovieBookingInfo(ticketCount: Int, movieDate: String, movieTime: String)
    }
}