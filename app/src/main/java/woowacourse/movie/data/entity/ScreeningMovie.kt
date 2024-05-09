package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "screening_movie")
data class ScreeningMovie(
    @PrimaryKey val id: Long,
    @Embedded(prefix = "movie_")
    val movie: Movie,
    @Embedded(prefix = "theater_")
    val theater: MovieTheater,
    @ColumnInfo(name = "screen_date_times")
    val screenDateTimes: List<Map<LocalDate, List<LocalTime>>>,
) {
    companion object {
        val STUB =
            ScreeningMovie(
                0L,
                Movie.STUB,
                MovieTheater.STUB_A,
                listOf(
                    mapOf(LocalDate.of(2024, 3, 1) to listOf(LocalTime.of(9, 0, 0))),
                    mapOf(LocalDate.of(2024, 3, 2) to listOf(LocalTime.of(10, 0, 0))),
                ),
            )
    }
}
