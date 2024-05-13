package woowacourse.movie.model.data.screeningref

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
