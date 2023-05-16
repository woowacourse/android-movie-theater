package com.woowacourse.domain.movie

import java.time.LocalDate

data class Movie(
    val poster: Int,
    val title: String,
    val runningTime: Int,
    val synopsis: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
