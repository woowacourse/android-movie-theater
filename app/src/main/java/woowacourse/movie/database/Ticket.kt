package woowacourse.movie.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val movieName: String,
    val seatNumbers: String,
    val ticketPrice: Int,
)
