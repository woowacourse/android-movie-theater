package woowacourse.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TicketInfo(
    @PrimaryKey(autoGenerate = true) val tid: Int,
    @ColumnInfo(name = "movieTitle") val movieTitle: String?,
    @ColumnInfo(name = "dateTime") val dateTime: String?,
    @ColumnInfo(name = "theaterName") val theaterName: String?,
    @ColumnInfo(name = "personnel") val personnel: Int?,
    @ColumnInfo(name = "seats") val seats: String?,
)
