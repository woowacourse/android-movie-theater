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
    }

    interface Presenter {
        fun setTicketCount(count: Int)
        fun requireMovieModel(movieId: Long): MovieModel
        fun addTicket()
        fun subTicket()
        fun getScreeningDate(movieId: Long): List<LocalDate>
        fun reserveMovie(cinemaModel: CinemaModel, localDateTime: LocalDateTime)
    }
}
