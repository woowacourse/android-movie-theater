package woowacourse.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservation_details")
data class BookingHistoryDetails(
    @PrimaryKey(
        autoGenerate =
        true,
    ) val uid: Int = 0,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "ticket_count") val ticketCount: Int,
    @ColumnInfo(name = "total_price") val totalPrice: Int,
    @ColumnInfo(name = "selected_seats") val selectedSeats: String,
)
