package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "movie_reservation",
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = MovieTheater::class,
            parentColumns = ["id"],
            childColumns = ["theater_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
    ],
)
data class MovieReservation(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "movie_id") val movieId: Long,
    @ColumnInfo(name = "selected_seats") val selectedSeats: List<Map<Int, Int>>,
    @ColumnInfo(name = "screen_date_time") val screenDateTime: LocalDateTime,
    @ColumnInfo(name = "head_count") val headCount: Int,
    @ColumnInfo(name = "cancel_dead_line") val cancelDeadLine: Long = 0,
    @ColumnInfo(name = "theater_id") val theaterId: Long,
) {
    companion object {
        val STUB =
            MovieReservation(
                0L,
                0L,
                listOf(mapOf(0 to 0), mapOf(0 to 1)),
                LocalDateTime.of(2024, 3, 1, 9, 0),
                3,
                152,
                0,
            )
    }
}
