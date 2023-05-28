package woowacourse.movie.model.data.remote

import woowacourse.movie.model.data.storage.MovieTheaterStorage
import java.time.LocalTime

class DummyMovieTheaterStorage : MovieTheaterStorage {

    private val seolleung = listOf<MovieTimeTable>(
        MovieTimeTable(
            1,
            listOf(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalTime.of(13, 0),
                LocalTime.of(15, 0),
                LocalTime.of(17, 0),
                LocalTime.of(19, 0),
                LocalTime.of(21, 0)
            )
        ),
        MovieTimeTable(
            2,
            listOf(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalTime.of(13, 0),
                LocalTime.of(15, 0)
            )
        ),
        MovieTimeTable(
            3,
            listOf(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalTime.of(14, 0),
                LocalTime.of(16, 0),
                LocalTime.of(17, 0)

            )
        )
    )

    private val gangnam = listOf<MovieTimeTable>(
        MovieTimeTable(
            1,
            listOf(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalTime.of(17, 0),
                LocalTime.of(19, 0),
                LocalTime.of(21, 0)
            )
        ),
        MovieTimeTable(
            2,
            listOf(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalTime.of(13, 0),
                LocalTime.of(15, 0)
            )
        )
    )

    private val gamsil = listOf<MovieTimeTable>(
        MovieTimeTable(
            2,
            listOf(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalTime.of(13, 0),
                LocalTime.of(15, 0)
            )
        ),
        MovieTimeTable(
            3,
            listOf(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalTime.of(14, 0),
                LocalTime.of(16, 0),
                LocalTime.of(17, 0)

            )
        ),
        MovieTimeTable(
            4,
            listOf(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalTime.of(13, 0),
                LocalTime.of(15, 0),
                LocalTime.of(17, 0),
                LocalTime.of(19, 0),
                LocalTime.of(21, 0)
            )
        )
    )

    private val movieTheaterData: Map<String, List<MovieTimeTable>> =
        mapOf("선릉" to seolleung, "강남" to gangnam, "잠실" to gamsil)

    override fun getTheatersByMovieId(movieId: Long): List<String> =
        movieTheaterData.filter { eachData ->
            movieId in eachData.value.map { it.movieId }
        }.keys.toList()

    override fun getTheaterTimeTableByMovieId(movieId: Long, theater: String): List<LocalTime> =
        movieTheaterData[theater]?.find { it.movieId == movieId }?.timeTable
            ?: throw IllegalStateException(NULL_TIME_TABLE_ERROR)

    companion object {
        private const val NULL_TIME_TABLE_ERROR = "현재 검색하는 영화 id는 해당 영화관에 상영 정보가 없습니다."
    }
}

private data class MovieTimeTable(
    val movieId: Long,
    val timeTable: List<LocalTime>
)
