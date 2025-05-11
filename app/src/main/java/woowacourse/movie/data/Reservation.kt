package woowacourse.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class Reservation (
    @PrimaryKey(autoGenerate = true) var uid: Long = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "time") val time: String?,
    @ColumnInfo(name = "personnel") val personnel: Int?,
    @ColumnInfo(name = "seats") val seats: String?,
    @ColumnInfo(name = "theater") val theater: String?,
    @ColumnInfo(name = "price") val price: Int?,
)
