package woowacourse.movie.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Purchase(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val movieTitle: String,
    val seatNumbers: String,
    val ticketPrice: String,
    val timeDate: String,
    val cinemaName: String
)
