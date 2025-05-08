package woowacourse.movie.model.ticket

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.model.movie.MovieTime
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.theater.Theater
import java.io.Serializable
import java.time.LocalDate

@Entity(tableName = "movieTicket")
data class MovieTicket(
    @PrimaryKey(autoGenerate = true)
    val ticketId: Long = 0,
    val title: String,
    val selectedDate: LocalDate,
    val selectedTime: MovieTime,
    val seats: List<Seat>,
    val theater: Theater,
) : Serializable {
    fun price(): Int = seats.sumOf { seat -> seat.price }
}
