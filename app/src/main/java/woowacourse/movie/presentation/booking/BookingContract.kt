package woowacourse.movie.presentation.booking

import woowacourse.movie.presentation.model.CinemaModel
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.ReservationModel
import java.time.LocalDate
import java.time.LocalDateTime

interface BookingContract {
    interface View {
        val presenter: Presenter
        val cinemaModel: CinemaModel
        fun setTicketCount(count: Int)
    }

    interface Presenter {
        fun setTicketCount(count: Int)
        fun requireMovieModel(id: Long): MovieModel
        fun addTicket()
        fun subTicket()
        fun getScreeningDate(): List<LocalDate>
        fun reserveMovie(cinemaModel: CinemaModel, localDateTime: LocalDateTime): ReservationModel
    }
}
