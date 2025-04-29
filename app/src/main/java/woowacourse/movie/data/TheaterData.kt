package woowacourse.movie.data

import woowacourse.movie.domain.model.ScreeningInfo
import java.time.LocalTime

object TheaterData {
    val screeningInfos: List<ScreeningInfo> = listOf(
        ScreeningInfo(
            "선릉 극장",
            MovieData.movie1,
            listOf(9, 11, 15).map { LocalTime.of(it, 0) }
        ),
        ScreeningInfo(
            "잠실 극장",
            MovieData.movie1,
            listOf(10, 12, 14, 17, 20, 22).map { LocalTime.of(it, 0) }
        ),
        ScreeningInfo(
            "강남 극장",
            MovieData.movie1,
            listOf(15, 22).map { LocalTime.of(it, 0) }
        ),
        ScreeningInfo(
            "선릉 극장",
            MovieData.movie2,
            listOf(12, 16, 20).map { LocalTime.of(it, 0) }
        )
    )
}