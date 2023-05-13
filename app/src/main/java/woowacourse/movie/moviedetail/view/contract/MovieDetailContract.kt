package woowacourse.movie.moviedetail.view.contract

import domain.TicketCount
import woowacourse.movie.movielist.dto.MovieDto
import woowacourse.movie.theater.dto.MovieTheaterDto
import java.time.LocalDate

interface MovieDetailContract {
    interface View {
        fun showMovieInfo(movieDto: MovieDto)
        fun formatDate(startDate: LocalDate, endDate: LocalDate): String
        fun formatRunningTime(runningTime: Int): String
        fun showTicketCount(numberOfPeople: Int)
        fun onClickBookBtnListener(movie: MovieDto, theater: MovieTheaterDto)
        fun setDateSpinner(intervalDate: List<String>)
        fun setTimeSpinner()
    }

    interface Presenter {
        var movieTicket: TicketCount

        fun getData(movieDto: MovieDto, theaterDto: MovieTheaterDto)
        fun updateMovieInfo()
        fun subTicketCount()
        fun plusTicketCount()
        fun setBookingButton()
        fun setSpinner()
        fun getIntervalDays(): List<String>
        fun getTimes(): List<String>
    }
}
