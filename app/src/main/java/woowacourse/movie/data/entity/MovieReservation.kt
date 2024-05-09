package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Seat
import java.time.LocalDateTime

@Entity(
    tableName = "movie_reservation",
    foreignKeys = [
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
    @Embedded(prefix = "movie_") val movie: Movie,
    @ColumnInfo(name = "selected_seats") val selectedSeats: List<Map<Int, Int>>,
    @ColumnInfo(name = "screen_date_time") val screenDateTime: LocalDateTime,
    @ColumnInfo(name = "head_count") val headCount: Int,
    @ColumnInfo(name = "theater_id") val theaterId: Long,
) {
    fun toMovieReservation(): woowacourse.movie.model.MovieReservation =
        woowacourse.movie.model.MovieReservation(
            id,
            movie.toMovie(),
            selectedSeats.toSelectedSeats(),
            screenDateTime,
            HeadCount(headCount),
            theaterId = theaterId,
        )

    private fun List<Map<Int, Int>>.toSelectedSeats(): List<Seat> =
        selectedSeats.flatMap { map ->
            map.entries.map {
                Seat(
                    woowacourse.movie.model.MovieTheater.seatSystem.rateOfSeat(it.key),
                    it.key,
                    it.value,
                )
            }
        }

    companion object {
        val STUB =
            MovieReservation(
                0L,
                Movie.STUB,
                listOf(mapOf(0 to 0), mapOf(0 to 1)),
                LocalDateTime.of(2024, 3, 1, 9, 0),
                3,
                0,
            )
    }
}
