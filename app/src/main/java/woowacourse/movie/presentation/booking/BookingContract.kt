package woowacourse.movie.presentation.booking

import woowacourse.movie.presentation.model.CinemaModel
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.ReservationModel
import java.time.LocalDate
import java.time.LocalDateTime

interface BookingContract {
    interface View {
        val presenter: Presenter
        fun setTicketCount(count: Int)
        fun reservationMovie(reservationModel: ReservationModel)
        fun setMovieInfo(movieModel: MovieModel)
        fun setScreeningDates(localDates: List<LocalDate>)
    }

    interface Presenter {
        fun setTicketCount(count: Int)
        fun setMovieInfo(movieId: Long)
        fun addTicket()
        fun subTicket()
        fun initMovieDates(movieId: Long)
        fun reserveMovie(cinemaModel: CinemaModel, localDateTime: LocalDateTime)
    }
}
