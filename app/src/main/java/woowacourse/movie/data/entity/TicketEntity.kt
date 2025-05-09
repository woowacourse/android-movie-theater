package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.HeadCount
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.seat.Seats
import java.time.LocalDateTime

@Entity(tableName = "ticket")
data class TicketEntity(
    @ColumnInfo(name = "movie") val movie: Movie,
    @ColumnInfo(name = "theater") val theater: Theater,
    @ColumnInfo(name = "showtime") val showtime: LocalDateTime,
    @ColumnInfo(name = "head_count") val headCount: HeadCount,
    @ColumnInfo(name = "seats") val seats: Seats,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
