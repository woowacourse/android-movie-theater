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

fun TicketEntity.toTicket(): Ticket = Ticket(seatLabel.toDomain(type), price)

fun String.toDomain(type: String): Seat {
    require(length >= 2) { "좌석 라벨은 최소 2글자 이상이어야 합니다." }
    require(this[0].isLetter()) { "좌석 라벨의 첫 글자는 문자여야 합니다." }
    require(substring(1).all { it.isDigit() }) { "좌석 라벨의 두 번째 문자부터는 숫자여야 합니다." }

    val rowChar = this[0]
    val colNum = this.substring(1).toInt()

    val row = rowChar - 'A'
    val col = colNum - 1
    require(row >= 0) { "좌석 행 인덱스는 0 이상이어야 합니다." }
    require(col >= 0) { "좌석 열 인덱스는 0 이상이어야 합니다." }
    return Seat(row, col, SeatType.valueOf(type))
}