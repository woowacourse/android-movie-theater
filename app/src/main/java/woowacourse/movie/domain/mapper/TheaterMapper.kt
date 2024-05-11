package woowacourse.movie.domain.mapper

import woowacourse.movie.data.database.theater.TheaterEntity
import woowacourse.movie.domain.Theater
import java.time.LocalTime

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
