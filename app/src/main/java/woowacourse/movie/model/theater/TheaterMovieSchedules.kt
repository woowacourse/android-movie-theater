package woowacourse.movie.model.theater

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.Movie.Companion.posterImages
import woowacourse.movie.model.movie.MovieTime
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class TheaterMovieSchedules(
    val value: List<TheaterMovieSchedule> = values,
) : Serializable {
    fun findMovieScreeningInfoByTheaterById(movieId: Long): List<TheaterMovieSchedule> =
        value.filter {
            it.movie.id == movieId
        }

    companion object {
        private val theaters =
            listOf(
                "메가박스 신촌",
                "CGV 강남",
                "롯데시네마 홍대",
                "CGV 용산",
                "메가박스 코엑스",
                "롯데시네마 건대",
                "CGV 압구정",
                "메가박스 상암",
                "CGV 수유",
                "롯데시네마 수원",
                "CGV 부천",
                "메가박스 부평",
                "CGV 인천",
                "롯데시네마 청량리",
                "CGV 일산",
                "메가박스 동대문",
                "CGV 왕십리",
                "롯데시네마 노원",
                "CGV 구로",
                "메가박스 이수",
            )

        private val screeningTimesSamples =
            listOf(
                listOf(LocalTime.of(10, 0), LocalTime.of(13, 0)),
                listOf(LocalTime.of(14, 30), LocalTime.of(18, 0)),
                listOf(LocalTime.of(11, 45), LocalTime.of(15, 15), LocalTime.of(19, 0)),
                listOf(LocalTime.of(9, 30), LocalTime.of(12, 15), LocalTime.of(16, 45)),
            )

        val values =
            (1..1000).map { index ->
                val movieId = ((index - 1) % 50) + 1L
                val poster = posterImages[(index - 1) % posterImages.size]
                val theaterName = theaters[index % theaters.size]
                val screeningTimes =
                    screeningTimesSamples[index % screeningTimesSamples.size].map { MovieTime(it) }

                TheaterMovieSchedule(
                    theater = Theater(name = theaterName),
                    movie =
                        Movie(
                            id = movieId,
                            title = "해리포터 $movieId",
                            poster = poster,
                            startDate = LocalDate.of(2025, 4, (movieId % 28 + 1).toInt()),
                            endDate = LocalDate.of(2025, 5, (movieId % 28 + 1).toInt()),
                            runningTime = 100 + (movieId % 60).toInt(),
                        ),
                    screeningInfo =
                        ScreeningInfo(
                            screeningTimes = screeningTimes,
                        ),
                )
            }
    }
}
