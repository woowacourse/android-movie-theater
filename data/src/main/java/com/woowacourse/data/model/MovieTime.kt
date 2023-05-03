package com.woowacourse.data.model

import woowacourse.movie.domain.model.movie.MovieTime

typealias DataMovieTime = MovieTime

data class MovieTime(
    val hour: Int,
    val min: Int,
)
