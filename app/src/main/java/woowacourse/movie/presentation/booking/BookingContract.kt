package woowacourse.movie.presentation.booking

import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.ReservationModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface BookingContract {
    interface View {
        val presenter: Presenter
        val movieId: Long
        fun convertTimeItems(position: Int)
        fun setTicketCount(count: Int)
    }

    interface Presenter {
        fun requireMovieModel(id: Long): MovieModel
        fun addTicket()
        fun subTicket()
        fun getScreeningDate(): List<LocalDate>
        fun getScreeningTime(position: Int): List<LocalTime>
        fun reserveMovie(localDateTime: LocalDateTime): ReservationModel
    }
}
