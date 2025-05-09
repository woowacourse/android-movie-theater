package woowacourse.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.booking.AdmissionCount
import woowacourse.movie.domain.model.seat.Col
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.ticket.Ticket
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "tickets")
data class TicketEntity(
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "screening_date") val screeningDate: String,
    @ColumnInfo(name = "screening_time") val screeningTime: String,
    val count: Int,
    val seats: String,
    val price: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var pk: Int = 0
}

fun Ticket.toEntity(): TicketEntity {
    return TicketEntity(
        movieTitle,
        theaterName,
        screeningDate.toString(),
        screeningTime.toString(),
        count.value,
        seats.joinToString(",") { seat -> "${seat.col.value} ${seat.row.value}" },
        price,
    )
}

fun TicketEntity.toDomain(): Ticket {
    val seats =
        seats.split(",").map { seat ->
            val (x, y) = seat.split(" ").map(String::toInt)
            Seat(Col(x), Row(y))
        }.toSet()

    return Ticket(
        movieTitle,
        theaterName,
        LocalDate.parse(screeningDate),
        LocalTime.parse(screeningTime),
        AdmissionCount(count),
        seats,
        price,
    )
}
