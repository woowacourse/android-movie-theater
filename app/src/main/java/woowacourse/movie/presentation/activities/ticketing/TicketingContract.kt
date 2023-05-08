package woowacourse.movie.presentation.activities.ticketing

import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.item.Movie

interface TicketingContract {
    interface View {
        val presenter: Presenter

        fun setTicketCountText(movieTicket: Ticket)
        fun startSeatPickerActivity()
        fun showToast(message: String)
        fun setMovieDetails(movie: Movie)
        fun setMovieTimes()
    }

    interface Presenter {
        fun addTicketCount()
        fun subTicketCount()
        fun setTicketCount()

        fun moveNextActivity(selectedDate: MovieDate?, selectedTime: MovieTime?)
        fun getMovieTicket(): Ticket
        fun updateMovieDetails()
        fun updateMovieTimes()
    }
}
