package woowacourse.movie.domain.model.movieticket

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_ticket")
data class MovieTicket(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val theaterName: String,
    val movieTitle: String,
    val screeningDate: String, // yyyy-MM-dd
    val screeningTime: String, // HH:mm
    val reservationCount: Int,
    val reservationSeats: String,
    val totalPrice: Int
)
