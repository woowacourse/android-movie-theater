package woowacourse.movie.model.movie

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = TicketContract.TABLE_TICKET)
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val theater: String,
    val screeningStartDateTime: String,
    val reservationCount: Int,
    val seats: String,
    val price: Int,
)

@Parcelize
data class ReservationDetail(
    val title: String,
    val theater: String,
    val screeningDateTime: LocalDateTime,
    val count: Int,
) : Parcelable

data class UserTicket(
    val title: String,
    val theater: String,
    val screeningStartDateTime: LocalDateTime,
    val seatInformation: SeatInformation,
)

fun UserTicket.toTicketEntity(): TicketEntity =
    TicketEntity(
        title = title,
        theater = theater,
        screeningStartDateTime = screeningStartDateTime.format(DateTimeFormatter.ofPattern("yyyy.M.d HH:mm")),
        reservationCount = seatInformation.reservationCount,
        seats = seatInformation.selectedSeat.joinToString(),
        price = seatInformation.totalSeatAmount()
    )

fun TicketEntity.toUserTicket(): UserTicket {
    val seat = if (seats.isEmpty()) mutableListOf() else seats.split(", ").map {
        Seat(SeatRow.findRow(it[0].digitToInt()), it[1].digitToInt())
    }.toMutableList()
    return UserTicket(
        title = title,
        theater = theater,
        screeningStartDateTime = LocalDateTime.parse(screeningStartDateTime, DateTimeFormatter.ofPattern("yyyy.M.d HH:mm")),
        seatInformation = SeatInformation(
            reservationCount,
            seat
        )
    )
}
