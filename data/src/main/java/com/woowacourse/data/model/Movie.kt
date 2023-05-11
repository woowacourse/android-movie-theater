package com.woowacourse.data.model

import java.time.LocalDate

typealias DataMovie = Movie

data class Movie(
    val id: Int = 0,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
    val introduce: String,
    val thumbnail: Int,
)
