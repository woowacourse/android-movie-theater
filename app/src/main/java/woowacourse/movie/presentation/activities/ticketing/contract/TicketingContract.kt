package woowacourse.movie.presentation.activities.ticketing.contract

import android.os.Bundle
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.movieitem.Movie

interface TicketingContract {
    interface View {
        val presenter: Presenter

        fun saveViewState(
            outState: Bundle,
            ticket: Ticket,
            movieDate: MovieDate?,
            movieTime: MovieTime?
        )

        fun restoreViewState(ticketCount: Int, movieDatePos: Int, movieTimePos: Int)
        fun initView(movieDates: List<MovieDate>)
        fun updateCount(value: Int)
        fun showUnSelectDateTimeAlertMessage()
        fun startSeatPickerActivity(
            movie: Movie,
            ticket: Ticket,
            selectedDate: MovieDate,
            selectedTime: MovieTime
        )

        fun updateRunningTimes(runningTimes: List<MovieTime>)
        fun updateSpinnersState(movieDatePos: Int, movieTimePos: Int)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun onCreate()
        abstract fun onSaveState(outState: Bundle)
        abstract fun onRestoreState(movieTicket: Ticket, movieDate: MovieDate, movieTime: MovieTime)
        abstract fun plusCount()
        abstract fun minusCount()
        abstract fun onClickTicketingButton()
        abstract fun onSelectMovieDate(position: Int)
        abstract fun onSelectMovieTime(position: Int)
    }
}
