package woowacourse.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.domain.ticket.Reservation
import java.time.LocalDateTime

@Entity(tableName = "reservation")
data class ReservationEntity(
    val title: String,
    val showtime: LocalDateTime,
    val seats: Set<Seat>,
    val cinemaName: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
) {
    fun toDomain(): Reservation = Reservation(title, showtime, seats, cinemaName)
}

fun Reservation.toEntity() = ReservationEntity(title, showtime, seats, cinemaName)
