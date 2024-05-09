package woowacourse.movie.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservationHistory")
data class ReservationHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "screeningDate")
    val screeningDate: String,
    @ColumnInfo(name = "screeningTime")
    val screeningTime: String,
    @ColumnInfo(name = "theaterName")
    val theaterName: String,
    @ColumnInfo(name = "movieName")
    val movieName: String,
)
