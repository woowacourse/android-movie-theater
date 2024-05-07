package woowacourse.movie.list.model

import woowacourse.movie.common.MovieDataSource.movieList
import java.time.LocalTime

object TheaterData {
    val screeningTimes1 =
        listOf(
            LocalTime.of(9, 0, 0),
            LocalTime.of(11, 0, 0),
            LocalTime.of(17, 0, 0),
            LocalTime.of(23, 0, 0),
        )

    val screeningTimes2 =
        listOf(
            LocalTime.of(10, 0, 0),
            LocalTime.of(12, 0, 0),
            LocalTime.of(14, 0, 0),
            LocalTime.of(16, 0, 0),
            LocalTime.of(18, 0, 0),
        )
    val screeningTimes3 =
        listOf(
            LocalTime.of(10, 0, 0),
            LocalTime.of(12, 0, 0),
            LocalTime.of(14, 0, 0),
            LocalTime.of(16, 0, 0),
            LocalTime.of(18, 0, 0),
            LocalTime.of(19, 0, 0),
            LocalTime.of(22, 0, 0),
        )
    val screeningTimes4 =
        listOf(
            LocalTime.of(10, 0, 0),
            LocalTime.of(20, 0, 0),
        )

    val screeningTimes5 =
        listOf(
            LocalTime.of(15, 0, 0),
        )

    val theaters =
        listOf(
            Theater(
                "선릉 극장",
                mapOf(
                    movieList[0] to screeningTimes1,
                    movieList[2] to screeningTimes2,
                    movieList[5] to screeningTimes3,
                    movieList[6] to screeningTimes1,
                ),
                0,
            ),
            Theater(
                "잠실 극장",
                mapOf(
                    movieList[1] to screeningTimes1,
                    movieList[2] to screeningTimes2,
                    movieList[3] to screeningTimes5,
                    movieList[5] to screeningTimes2,
                    movieList[6] to screeningTimes4,
                    movieList[8] to screeningTimes3,
                ),
                1,
            ),
            Theater(
                "강남 극장",
                mapOf(
                    movieList[0] to screeningTimes2,
                    movieList[6] to screeningTimes4,
                ),
                2,
            ),
        )
}
