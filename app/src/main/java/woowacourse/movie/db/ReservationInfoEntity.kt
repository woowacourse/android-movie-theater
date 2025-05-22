package woowacourse.movie.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime

@Entity(tableName = "reservation")
data class ReservationInfoEntity(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "reservation_date_time") val reservationDateTime: LocalDateTime,
    @ColumnInfo(name = "reservation_count") val reservationCount: Int,
    @ColumnInfo(name = "seats") val seats: List<Seat>,
    @ColumnInfo(name = "cinema") val cinema: String,
    @ColumnInfo(name = "price") val price: Int,
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
)
