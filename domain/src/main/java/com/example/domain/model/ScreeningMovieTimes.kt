package com.example.domain.model

import java.time.LocalTime

class ScreeningMovieTimes(
    val movie: Movie,
    val screeningTimes: List<LocalTime>
)
