package woowacourse.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservation")
data class Reservation(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "theater") val theater: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "headCount") val headCount: Int,
    @ColumnInfo(name = "selectedDate") val selectedDate: String,
    @ColumnInfo(name = "selectedTime") val selectedTime: String,
    @ColumnInfo(name = "seats") val seats: String,
    @ColumnInfo(name = "price") val price: String,
)
