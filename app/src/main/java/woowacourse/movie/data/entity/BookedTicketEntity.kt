package woowacourse.movie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.theater.Seats
import java.time.LocalDateTime

@Entity(tableName = "booked_tickets")
data class BookedTicketEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val movieName: String,
    val headcount: Headcount,
    val dateTime: LocalDateTime,
    val seats: Seats,
    val theaterName: String,
)
