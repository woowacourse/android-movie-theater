package woowacourse.movie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class TicketEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val count: Int,
    val showtime: LocalDateTime,
)
