package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.model.MovieSelectedSeats
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "tickets")
data class Ticket(
    @ColumnInfo(name = "movie_id") val movieId: Long,
    @ColumnInfo(name = "screening_date") val screeningDate: LocalDate,
    @ColumnInfo(name = "screening_time") val screeningTime: LocalTime,
    @ColumnInfo(name = "selected_seats") val selectedSeats: MovieSelectedSeats,
    @ColumnInfo(name = "theater_name") val theaterName: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
