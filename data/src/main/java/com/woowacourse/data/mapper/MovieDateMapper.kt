package com.woowacourse.data.mapper

import com.woowacourse.data.model.DataMovieDate
import woowacourse.movie.domain.model.movie.DomainMovieDate

fun DataMovieDate.toDomain(): DomainMovieDate =
    DomainMovieDate(year = year, month = month, day = day)

fun DomainMovieDate.toData(): DataMovieDate =
    DataMovieDate(year = year, month = month, day = day)
