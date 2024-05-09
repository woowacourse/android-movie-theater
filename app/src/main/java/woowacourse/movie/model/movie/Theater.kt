package woowacourse.movie.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.model.movie.TheaterContract.TABLE_THEATER
import java.time.LocalTime

data class Theater(
    val id: Long = 0,
    val name: String,
    val screeningTimes: List<LocalTime>,
)

@Entity(tableName = TABLE_THEATER)
data class TheaterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val screeningTimes: List<String>,
)

fun TheaterEntity.toTheater() =
    Theater(
        id = id,
        name = name,
        screeningTimes = screeningTimes.map { LocalTime.parse(it) },
    )

fun Theater.toTheaterEntity() =
    TheaterEntity(
        id = id,
        name = name,
        screeningTimes = screeningTimes.map(LocalTime::toString),
    )
