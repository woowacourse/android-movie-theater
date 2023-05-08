package woowacourse.movie.presentation.ui.ticketing.contract

import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.TicketingState
import woowacourse.movie.presentation.model.movieitem.Movie

interface TicketingContract {
    interface View {
        val presenter: Presenter

        fun updateCount(value: Int)
        fun showTicketingState(ticketCount: Int, movieDatePos: Int, movieTimePos: Int)
        fun showSeatPickerScreen(ticketingState: TicketingState)
        fun updateRunningTimes(runningTimes: List<MovieTime>)
        fun updateSpinnersState(movieDatePos: Int, movieTimePos: Int)
        fun showUnSelectDateTimeAlertMessage()
        fun showNotExistSelectableDates()
        fun showMovieIntroduce(movie: Movie)
        fun updateMovieDates(movieDates: List<MovieDate>)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun getState(): TicketingState
        abstract fun setState(ticketingState: TicketingState)
        abstract fun plusCount()
        abstract fun minusCount()
        abstract fun doTicketing()
        abstract fun changeMovieDate(position: Int)
        abstract fun changeMovieTime(position: Int)
    }
}
