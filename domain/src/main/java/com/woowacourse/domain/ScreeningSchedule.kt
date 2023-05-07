package com.woowacourse.domain

import com.woowacourse.domain.movie.Movie
import java.time.LocalTime

class ScreeningSchedule(
    val movie: Movie,
    val screeningTime: List<LocalTime>,
)
