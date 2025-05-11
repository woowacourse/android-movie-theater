package woowacourse.movie.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime

@Entity(tableName = "reservation")
data class ReservationInfoEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "title") val title: Long,
    @ColumnInfo(name = "reservation_date_time") val reservationDateTime: LocalDateTime,
    @ColumnInfo(name = "reservation_count") val reservationCount: Int,
    @ColumnInfo(name = "seats") val seats: List<Seat>,
    @ColumnInfo(name = "cinema") val cinema: String,
    @ColumnInfo(name = "price") val price: Int,
)
