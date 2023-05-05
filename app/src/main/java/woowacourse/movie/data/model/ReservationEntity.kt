package woowacourse.movie.data.model

import woowacourse.movie.domain.Money
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.Seat
import java.time.LocalDateTime

data class ReservationEntity(
    val id: Int,
    val movieId: Int,
    val screeningDateTime: String,
    val fee: Int
) {
    fun toDomain(
        movie: Movie,
        seats: List<Seat>
    ): Reservation {
        return Reservation(
            id,
            movie,
            seats,
            LocalDateTime.parse(screeningDateTime),
            Money(fee)
        )
    }

    companion object {
        const val TABLE_NAME = "RESERVATION"
        const val ID_COLUMN = "ID"
        const val MOVIE_ID_COLUMN = "MOVIE_ID"
        const val SCREENING_DATETIME_COLUMN = "SCREENING_DATETIME"
        const val FEE_COLUMN = "FEE"
    }
}
