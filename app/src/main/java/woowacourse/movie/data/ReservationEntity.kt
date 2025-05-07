package woowacourse.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.Seats
import java.time.LocalDateTime

@Entity(tableName = "reservations")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "movie_title") val title: String,
    @ColumnInfo(name = "date_time") val dateTime: LocalDateTime,
    @ColumnInfo(name = "seats") val seats: Seats,
)
