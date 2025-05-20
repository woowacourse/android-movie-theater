package woowacourse.movie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "ticket")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val count: Int,
    val showtime: LocalDateTime,
    val cinemaName: String,
    val seats: String,
    val purchaseType: String,
)
