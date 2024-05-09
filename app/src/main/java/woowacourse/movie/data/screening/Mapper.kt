package woowacourse.movie.data.screening

import woowacourse.movie.model.Screening
import java.time.ZoneOffset

fun Screening.toDto() =
    ScreeningDto(
        id,
        movie.id,
        theater.id,
        localDateTime.toEpochSecond(ZoneOffset.UTC),
    )

/*
fun ScreeningDto.toScreening() =
    Screening(
        id,
        movieId,
        theaterId,
        LocalDateTime.ofEpochSecond(screeningTimestamp, 0, ZoneOffset.UTC)
    )
 */
