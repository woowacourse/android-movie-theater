package woowacourse.movie.data

import woowacourse.movie.domain.model.ScreeningInfo
import woowacourse.movie.domain.model.Theater
import java.time.LocalTime

object TheaterData {
    val theaters: List<Theater> = listOf(
        Theater(
            "선릉 극장",
            setOf(
                ScreeningInfo(
                    MovieData.movie1,
                    listOf(9, 11, 15).map { LocalTime.of(it, 0) }
                ),
                ScreeningInfo(
                    MovieData.movie2,
                    listOf(10, 14, 17, 19).map { LocalTime.of(it, 0) }
                ),
                ScreeningInfo(
                    MovieData.movie3,
                    listOf(10, 13, 18, 21).map { LocalTime.of(it, 0) }
                )
            )
        ),
        Theater(
            "잠실 극장",
            setOf(
                ScreeningInfo(
                    MovieData.movie1,
                    listOf(12, 14, 17, 20, 22).map { LocalTime.of(it, 0) }
                ),
                ScreeningInfo(
                    MovieData.movie4,
                    listOf(9, 11, 15).map { LocalTime.of(it, 0) }
                ),
                ScreeningInfo(
                    MovieData.movie5,
                    listOf(13, 15, 17, 19).map { LocalTime.of(it, 0) }
                ),
                ScreeningInfo(
                    MovieData.movie6,
                    listOf(8, 11, 14, 16).map { LocalTime.of(it, 0) }
                )
            )
        ),
        Theater(
            "강남 극장",
            setOf(
                ScreeningInfo(
                    MovieData.movie1,
                    listOf(11, 13, 18, 21, 23).map { LocalTime.of(it, 0) }
                ),
                ScreeningInfo(
                    MovieData.movie3,
                    listOf(8, 15, 19).map { LocalTime.of(it, 0) }
                ),
                ScreeningInfo(
                    MovieData.movie6,
                    listOf(10, 15, 17, 19).map { LocalTime.of(it, 0) }
                ),
                ScreeningInfo(
                    MovieData.movie7,
                    listOf(11, 14, 19, 22).map { LocalTime.of(it, 0) }
                )
            )
        ),
    )
}