package woowacourse.movie.movie.moviedetail

import domain.movieinfo.Movie
import woowacourse.movie.movie.dto.movie.MovieDateDto
import woowacourse.movie.movie.dto.movie.MovieDto
import woowacourse.movie.movie.dto.ticket.TicketCountDto
import java.time.LocalDate

interface MovieDetailContract {
    interface View {
        val presenter: Presenter

        fun showMovieInfo(poster: Int, title: String, description: String)
        fun showMovieDateInfo(date: String, time: String)
        fun formatMovieRunningDate(startDate: LocalDate, endDate: LocalDate): String
        fun formatMovieRunningTime(runningTime: Int): String
        fun showNumberOfPeople()
        fun onClickDecreaseBtnListener()
        fun onClickIncreaseBtnListener()
        fun onClickBookBtnListener(movie: MovieDto)
        fun setDateSpinner(intervalDate: List<String>)
        fun setTimeSpinner(selectedDay: MovieDateDto)
    }

    interface Presenter {
        fun initActivity(movieDto: MovieDto)
        fun getMovieDate(movie: Movie): String
        fun getMovieTime(movie: Movie): String
        fun decreaseCount(movieTicket: TicketCountDto): String
        fun increaseCount(movieTicket: TicketCountDto): String
        fun getIntervalDays(movie: Movie): List<String>
    }
}
