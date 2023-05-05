package com.woowacourse.data.mapper

import com.woowacourse.data.model.DataTheater
import woowacourse.movie.domain.model.theater.DomainTheater

fun DataTheater.toDomain(): DomainTheater = DomainTheater(
    name = name,
    date = date.toDomain(),
    runningTimes = runningTimes.map { it.toDomain() }
)

fun DomainTheater.toData(movieId: Int): DataTheater = DataTheater(
    movieId = movieId,
    name = name,
    date = date.toData(),
    runningTimes = runningTimes.map { it.toData() }
)
