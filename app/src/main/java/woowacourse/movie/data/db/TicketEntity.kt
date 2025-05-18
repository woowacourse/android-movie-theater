package woowacourse.movie.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.PeopleCount
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seat
import java.time.LocalDateTime

@Entity(tableName = "table_booking")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "booking_schedule") val bookingDateTime: LocalDateTime,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "ticket_count") val ticketCount: Int,
    @ColumnInfo(name = "ticket_price") val ticketPrice: Int,
    @ColumnInfo(name = "seats") val seats: Set<Seat>,
) {
    fun toDomain(): Ticket {
        return Ticket(
            id = id,
            title = movieTitle,
            bookingDate = bookingDateTime.toLocalDate(),
            bookingTime = bookingDateTime.toLocalTime(),
            theaterName = theaterName,
            count = PeopleCount(ticketCount),
            price = ticketPrice,
            seats = seats,
        )
    }
}
