package com.woowacourse.data.mapper

import com.woowacourse.data.model.DataMovie
import woowacourse.movie.domain.model.movie.Movie

fun DataMovie.toDomain(): Movie =
    Movie(
        id = id,
        title = title,
        startDate = startDate,
        endDate = endDate,
        runningTime = runningTime,
        introduce = introduce,
        thumbnail = thumbnail
    )

fun Movie.toData(): DataMovie = DataMovie(
    id = id,
    title = title,
    startDate = startDate,
    endDate = endDate,
    runningTime = runningTime,
    introduce = introduce,
    thumbnail = thumbnail
)
