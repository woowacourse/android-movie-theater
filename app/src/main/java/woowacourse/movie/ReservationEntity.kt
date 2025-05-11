package woowacourse.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.reservation.Seat

@Entity(tableName = "reservation")
data class ReservationEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val showtime: String,
    val seats: Set<Seat>,
    val cinemaName: String,
)
