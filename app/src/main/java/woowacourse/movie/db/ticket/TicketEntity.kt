package woowacourse.movie.db.ticket

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.Ticket
import java.io.Serializable
import java.time.LocalDateTime

@Entity(tableName = "reservation_history")
data class TicketEntity(
    @ColumnInfo(name = "screening_date_time") val screeningDateTime: LocalDateTime,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "seats") val seats: Seats,
    @PrimaryKey(autoGenerate = true) var uid: Long? = null,
) : Serializable {
    companion object {
        fun TicketEntity.toDomain() =
            Ticket(
                screeningDateTime = screeningDateTime,
                movieTitle = movieTitle,
                theaterName = theaterName,
                seats = seats,
                uid = this.uid,
            )
    }
}
