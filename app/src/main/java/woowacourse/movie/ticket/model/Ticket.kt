package woowacourse.movie.ticket.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.detail.model.Count
import woowacourse.movie.seats.model.Seat

data class Ticket(
    val movieId: Long,
    val screeningDate: String,
    val screeningTime: String,
    val seatsCount: Count,
    val seats: List<Seat>,
    val theaterId: Long,
    val price: Int,
)

@Entity(tableName = "ticketDb")
data class DbTicket(
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "screening_date") val screeningDate: String,
    @ColumnInfo(name = "screening_time") val screeningTime: String,
    @ColumnInfo(name = "seats_count") val seatsCount: Int,
    @ColumnInfo(name = "seats") val seats: String,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "price") val price: Int,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
