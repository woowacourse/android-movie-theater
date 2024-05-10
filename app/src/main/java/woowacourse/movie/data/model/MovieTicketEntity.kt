package woowacourse.movie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_ticket")
data class MovieTicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val theaterName: String,
    val movieTitle: String,
    val screeningDate: String,
    val screeningTime: String,
    val reservationCount: Int,
    val reservationSeats: String,
    val totalPrice: Int
)
