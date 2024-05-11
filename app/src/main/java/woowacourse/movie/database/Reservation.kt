package woowacourse.movie.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class Reservation(
    @PrimaryKey(autoGenerate = true) val ticketId: Long = 0L,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "screeningDate") val screeningDate: String,
    @ColumnInfo(name = "startTime") val startTime: String,
    @ColumnInfo(name = "endTime") val endTime: String,
    @ColumnInfo(name = "runningTime") val runningTime: Int,
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "theater") val theater: String,
    @ColumnInfo(name = "seats") val seats: List<String>,
    @ColumnInfo(name = "totalPrice") val totalPrice: Int,
)
