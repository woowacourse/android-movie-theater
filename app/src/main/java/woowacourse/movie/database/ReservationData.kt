package woowacourse.movie.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class ReservationData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "theater_id") val theaterId: Long,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "screen_date") val screenDate: String,
    @ColumnInfo(name = "screen_time") val screenTime: String,
    @ColumnInfo(name = "selected_seats") val selectedSeats: String,
    @ColumnInfo(name = "total_price") val totalPrice: String,
)
