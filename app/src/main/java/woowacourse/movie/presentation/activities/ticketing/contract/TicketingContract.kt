package woowacourse.movie.presentation.activities.ticketing.contract

import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.TicketingState
import woowacourse.movie.presentation.model.movieitem.Movie

interface TicketingContract {
    interface View {
        val presenter: Presenter

        fun initView(movie: Movie, movieDates: List<MovieDate>)
        fun updateCount(value: Int)
        fun showTicketingState(ticketCount: Int, movieDatePos: Int, movieTimePos: Int)
        fun showSeatPickerScreen(ticketingState: TicketingState)
        fun updateRunningTimes(runningTimes: List<MovieTime>)
        fun updateSpinnersState(movieDatePos: Int, movieTimePos: Int)
        fun showUnSelectDateTimeAlertMessage()
    }

    abstract class Presenter {
        private var view: View? = null

        open fun attach(view: View) {
            this.view = view
        }

        open fun detach() {
            this.view = null
        }

        protected fun requireView(): View =
            view ?: throw IllegalStateException("View is not attached")

        abstract fun getState(): TicketingState
        abstract fun setState(ticketingState: TicketingState)
        abstract fun plusCount()
        abstract fun minusCount()
        abstract fun onClickTicketingButton()
        abstract fun onSelectMovieDate(position: Int)
        abstract fun onSelectMovieTime(position: Int)
    }
}
