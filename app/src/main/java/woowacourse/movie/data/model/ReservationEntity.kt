package woowacourse.movie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime

@Entity(tableName = "reservationEntity")
data class ReservationEntity(
    val theaterId: Int,
    val movieId: Int,
    val title: String,
    val ticketCount: Int,
    val seats: List<Seat>,
    val dateTime: LocalDateTime,
) {
    @PrimaryKey(autoGenerate = true)
    var reservationId: Long = 0
}
