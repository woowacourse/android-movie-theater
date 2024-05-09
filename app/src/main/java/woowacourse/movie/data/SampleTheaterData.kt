package woowacourse.movie.data

import woowacourse.movie.data.SampleMovieData.movieList
import woowacourse.movie.domain.model.home.Theater
import java.time.LocalTime

object SampleTheaterData {
    val defaultTheater = Theater(
        "선릉 극장",
        mapOf(
            movieList[0] to listOf(LocalTime.of(9, 0, 0)),
        ),
        -1,
    )

    private val screeningTimes1 =
        listOf(
            LocalTime.of(9, 0, 0),
            LocalTime.of(11, 0, 0),
            LocalTime.of(17, 0, 0),
            LocalTime.of(23, 0, 0),
        )

    private val screeningTimes2 =
        listOf(
            LocalTime.of(10, 0, 0),
            LocalTime.of(12, 0, 0),
            LocalTime.of(14, 0, 0),
            LocalTime.of(16, 0, 0),
            LocalTime.of(18, 0, 0),
        )

    val theaters: List<Theater> =
        listOf(
            Theater(
                "선릉 극장",
                mapOf(
                    movieList[0] to screeningTimes1,
                    movieList[2] to screeningTimes2,
                    movieList[4] to screeningTimes2,
                    movieList[5] to screeningTimes1,
                ),
                0,
            ),
            Theater(
                "잠실 극장",
                mapOf(
                    movieList[0] to screeningTimes1,
                    movieList[2] to screeningTimes2,
                    movieList[4] to screeningTimes1,
                    movieList[5] to screeningTimes2,
                    movieList[6] to screeningTimes1,
                ),
                1,
            ),
            Theater(
                "강남 극장",
                mapOf(
                    movieList[0] to screeningTimes2,
                    movieList[5] to screeningTimes2,
                    movieList[6] to screeningTimes1,
                ),
                2,
            ),
        )
}
