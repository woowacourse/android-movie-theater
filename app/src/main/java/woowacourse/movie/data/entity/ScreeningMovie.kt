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
    @ColumnInfo(name = "movie") @Embedded(prefix = "movie_")
    val movie: Movie,
    @ColumnInfo(name = "movie_theater") @Embedded(prefix = "theater_")
    val theater: MovieTheater,
    @ColumnInfo(name = "screen_date_times")
    val screenDateTimes: List<Map<LocalDate, List<LocalTime>>>,
)
