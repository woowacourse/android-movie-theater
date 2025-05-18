package woowacourse.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime

@Entity(tableName = "booked_tickets")
data class BookedTicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "screening_date_time") val screeningDateTime: LocalDateTime,
    @ColumnInfo(name = "select_seats") val selectSeats: Set<Seat>,
    @ColumnInfo(name = "headcount") val headcount: Int,
)
