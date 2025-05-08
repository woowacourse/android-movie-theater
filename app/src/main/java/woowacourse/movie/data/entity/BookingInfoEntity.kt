package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking_info")
data class BookingInfoEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0L,
    @ColumnInfo(name = "movie_id") val movieId: Long,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "start_date") val startDate: String,
    @ColumnInfo(name = "end_date") val endDate: String,
    @ColumnInfo(name = "running_time") val runningTime: Int,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "date") val selectedDate: String,
    @ColumnInfo(name = "time") val selectedTime: String,
    @ColumnInfo(name = "seat_list") val seatList: String,
    @ColumnInfo(name = "ticket_count") val ticketCount: Int,
)
