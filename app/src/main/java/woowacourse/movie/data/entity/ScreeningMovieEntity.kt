package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.model.ScreenDateTime
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "screening_movie")
data class ScreeningMovieEntity(
    @PrimaryKey val id: Long,
    @Embedded(prefix = "movie_")
    val movie: Movie,
    @Embedded(prefix = "theater_")
    val theater: MovieTheater,
    @ColumnInfo(name = "screen_date_times")
    val screenDateTimes: List<Map<LocalDate, List<LocalTime>>>,
) {
    fun toScreeningMovie(): woowacourse.movie.model.ScreeningMovie {
        val transformedDateTimes =
            screenDateTimes.flatMap { map ->
                map.entries.map { entry ->
                    ScreenDateTime(date = entry.key, times = entry.value)
                }
            }
        return woowacourse.movie.model.ScreeningMovie(
            id = id,
            movie = movie.toMovie(),
            theater = theater.toMovieTheater(),
            screenDateTimes = transformedDateTimes,
        )
    }

    companion object {
        val STUB_A =
            ScreeningMovieEntity(
                0L,
                Movie.STUB,
                MovieTheater.STUB_A,
                listOf(
                    mapOf(LocalDate.of(2024, 3, 1) to listOf(LocalTime.of(9, 0, 0))),
                    mapOf(LocalDate.of(2024, 3, 2) to listOf(LocalTime.of(10, 0, 0))),
                ),
            )
        val STUB_B =
            ScreeningMovieEntity(
                1L,
                Movie.STUB,
                MovieTheater.STUB_B,
                listOf(
                    mapOf(LocalDate.of(2024, 3, 1) to listOf(LocalTime.of(9, 0, 0))),
                    mapOf(LocalDate.of(2024, 3, 2) to listOf(LocalTime.of(10, 0, 0))),
                ),
            )

        val STUB_C =
            ScreeningMovieEntity(
                2L,
                Movie.STUB,
                MovieTheater.STUB_C,
                listOf(
                    mapOf(
                        LocalDate.of(2024, 3, 1) to
                            listOf(
                                LocalTime.of(9, 0, 0),
                                LocalTime.of(10, 0, 0),
                                LocalTime.of(11, 0, 0),
                                LocalTime.of(12, 0, 0),
                            ),
                    ),
                    mapOf(
                        LocalDate.of(2024, 3, 2) to
                            listOf(
                                LocalTime.of(9, 0, 0),
                                LocalTime.of(10, 0, 0),
                                LocalTime.of(11, 0, 0),
                                LocalTime.of(12, 0, 0),
                            ),
                    ),
                    mapOf(
                        LocalDate.of(2024, 3, 3) to
                            listOf(
                                LocalTime.of(10, 0, 0),
                                LocalTime.of(11, 0, 0),
                                LocalTime.of(12, 0, 0),
                                LocalTime.of(13, 0, 0),
                            ),
                    ),
                    mapOf(
                        LocalDate.of(2024, 3, 4) to
                            listOf(
                                LocalTime.of(11, 0, 0),
                                LocalTime.of(12, 0, 0),
                                LocalTime.of(13, 0, 0),
                                LocalTime.of(14, 0, 0),
                            ),
                    ),
                ),
            )
    }
}
