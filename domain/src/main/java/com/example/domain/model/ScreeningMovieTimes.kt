package com.example.domain.model

import java.time.LocalTime

data class ScreeningMovieTimes(
    val movie: Movie,
    val screeningDateTimes: List<LocalTime>
)
