package com.example.domain.model

data class TheaterScreeningInfo(
    val theater: Theater,
    val screeningInfos: List<ScreeningMovieTimes>
)
