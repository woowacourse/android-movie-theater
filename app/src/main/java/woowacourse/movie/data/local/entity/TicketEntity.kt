package woowacourse.movie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.reservation.PurchaseType
import woowacourse.movie.domain.reservation.Seat
import java.time.LocalDateTime

@Entity(tableName = "ticket")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val count: Int,
    val showtime: LocalDateTime,
    val cinemaName: String,
    val seats: Set<Seat>,
    val purchaseType: PurchaseType = PurchaseType.DEFAULT,
)
