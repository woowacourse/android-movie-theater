package woowacourse.movie.db.ticket

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.model.seats.Seats
import java.io.Serializable
import java.time.LocalDateTime

@Entity(tableName = "reservation_history")
data class Ticket(
    @ColumnInfo(name = "screening_date_time") val screeningDateTime: LocalDateTime,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "seats") val seats: Seats,
    @PrimaryKey(autoGenerate = true) var uid: Long? = null,
) : Serializable {
    fun getSeatsAmount() = seats.calculateAmount()

    companion object {
        const val DEFAULT_TICKET_ID = -1L
    }
}
