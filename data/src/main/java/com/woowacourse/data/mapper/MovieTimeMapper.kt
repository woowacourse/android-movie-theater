package com.woowacourse.data.mapper

import com.woowacourse.data.model.DataMovieTime
import woowacourse.movie.domain.model.movie.DomainMovieTime

fun DataMovieTime.toDomain(): DomainMovieTime =
    DomainMovieTime(hour = hour, min = min)

fun DomainMovieTime.toData(): DataMovieTime =
    DataMovieTime(hour = hour, min = min)
