package com.example.domain.repository.dataSource

import com.example.domain.model.ScreeningMovieTimes
import com.example.domain.model.Theater
import com.example.domain.model.TheaterScreeningInfo
import java.time.LocalTime

val theaterDataSources = listOf<Theater>(
    Theater(0, "선릉 극장"),
    Theater(1, "잠실 극장"),
    Theater(2, "강남 극장"),
)

val theaterMovieScreeningTimesDataSources = listOf<TheaterScreeningInfo>(
    TheaterScreeningInfo(
        theaterDataSources[0],
        listOf(
            ScreeningMovieTimes(
                movieDataSources[0],
                listOf(
                    LocalTime.of(10, 0),
                    LocalTime.of(12, 0),
                    LocalTime.of(14, 0),
                    LocalTime.of(16, 0),
                    LocalTime.of(18, 0),
                    LocalTime.of(20, 0),
                )
            ),
            ScreeningMovieTimes(
                movieDataSources[1],
                listOf(
                    LocalTime.of(12, 0),
                    LocalTime.of(16, 0),
                    LocalTime.of(20, 0),
                )
            ),
            ScreeningMovieTimes(
                movieDataSources[2],
                listOf(
                    LocalTime.of(10, 0),
                    LocalTime.of(14, 0),
                    LocalTime.of(18, 0),
                )
            ),
        )
    ),
    TheaterScreeningInfo(
        theaterDataSources[1],
        listOf(
            ScreeningMovieTimes(
                movieDataSources[0],
                listOf(
                    LocalTime.of(10, 0),
                    LocalTime.of(16, 0),
                    LocalTime.of(20, 0),
                )
            ),
            ScreeningMovieTimes(
                movieDataSources[1],
                listOf(
                    LocalTime.of(10, 0),
                    LocalTime.of(12, 0),
                    LocalTime.of(14, 0),
                    LocalTime.of(16, 0),
                    LocalTime.of(18, 0),
                    LocalTime.of(20, 0),
                )
            ),
            ScreeningMovieTimes(
                movieDataSources[2],
                listOf(
                    LocalTime.of(12, 0),
                    LocalTime.of(20, 0),
                )
            ),
        )
    ),
    TheaterScreeningInfo(
        theaterDataSources[2],
        listOf(
            ScreeningMovieTimes(
                movieDataSources[0],
                listOf(
                    LocalTime.of(12, 0),
                    LocalTime.of(16, 0),
                    LocalTime.of(20, 0),
                )
            ),
            ScreeningMovieTimes(
                movieDataSources[2],
                listOf(
                    LocalTime.of(10, 0),
                    LocalTime.of(12, 0),
                    LocalTime.of(14, 0),
                    LocalTime.of(16, 0),
                    LocalTime.of(18, 0),
                    LocalTime.of(20, 0),
                )
            ),
        )
    )
)
