package woowacourse.movie.data.screening

import woowacourse.movie.model.ScreeningRef

fun ScreeningRef.toDto() =
    ScreeningRefDto(
        id,
        movieId,
        theaterId,
        screeningTimeStamp,
    )

fun ScreeningRefDto.toScreeningRef() =
    ScreeningRef(
        id,
        movieId,
        theaterId,
        screeningTimestamp,
    )
