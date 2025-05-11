package woowacourse.movie.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.cinema.screen.Seat
import woowacourse.movie.domain.model.cinema.screen.SeatType
import woowacourse.movie.domain.model.cinema.ticket.Ticket

@Entity(
    tableName = "ticket",
    foreignKeys = [
        ForeignKey(
            entity = TicketBundleEntity::class,
            parentColumns = ["id"],
            childColumns = ["bundleId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class TicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val seatLabel: String,
    val type: String,
    val price: Int,
    val bundleId: Int,
)

fun TicketEntity.toTicket(): Ticket = Ticket(seatLabel.toSeat(type), price)

fun String.toSeat(type: String): Seat {
    val rowChar = this[0] // 'A'
    val colNum = this.substring(1).toInt() // '1'

    val row = rowChar - 'A'
    val col = colNum - 1
    return Seat(row, col, SeatType.valueOf(type))
}
