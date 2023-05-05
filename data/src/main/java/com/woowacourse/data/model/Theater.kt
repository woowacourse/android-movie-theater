package com.woowacourse.data.model

typealias DataTheater = Theater

data class Theater(
    val id: Int = 0,
    val movieId: Int,
    val name: String,
    val date: DataMovieDate,
    val runningTimes: List<DataMovieTime>,
)
