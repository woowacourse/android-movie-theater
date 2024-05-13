package woowacourse.movie.model.data.theater

import woowacourse.movie.model.Theater

fun Theater.toDto() =
    TheaterDto(
        id,
        name,
    )

fun TheaterDto.toTheater() =
    Theater(
        id,
        name,
    )
