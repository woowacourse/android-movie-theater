package woowacourse.movie.repository

import woowacourse.movie.model.ScreeningMovie
import woowacourse.movie.model.Theater
import java.time.LocalTime

object DummyTheaterList : TheaterListRepository {
    override val list: List<Theater> =
        listOf(
            Theater(
                id = 1,
                name = "선릉",
                screeningMovies =
                    listOf(
                        ScreeningMovie(
                            1,
                            listOf(
                                LocalTime.of(10, 0),
                                LocalTime.of(12, 0),
                                LocalTime.of(14, 0),
                            ),
                        ),
                        ScreeningMovie(
                            2,
                            listOf(
                                LocalTime.of(11, 0),
                                LocalTime.of(13, 0),
                            ),
                        ),
                        ScreeningMovie(
                            3,
                            listOf(
                                LocalTime.of(9, 0),
                            ),
                        ),
                    ),
            ),
            Theater(
                id = 2,
                name = "잠실",
                screeningMovies =
                    listOf(
                        ScreeningMovie(
                            1,
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(15, 0),
                            ),
                        ),
                        ScreeningMovie(
                            2,
                            listOf(
                                LocalTime.of(13, 0),
                                LocalTime.of(15, 0),
                                LocalTime.of(17, 0),
                                LocalTime.of(19, 0),
                            ),
                        ),
                    ),
            ),
            Theater(
                id = 3,
                name = "강남",
                screeningMovies =
                    listOf(
                        ScreeningMovie(
                            1,
                            listOf(
                                LocalTime.of(11, 0),
                                LocalTime.of(17, 0),
                                LocalTime.of(19, 0),
                            ),
                        ),
                        ScreeningMovie(
                            2,
                            listOf(
                                LocalTime.of(7, 0),
                                LocalTime.of(9, 0),
                            ),
                        ),
                        ScreeningMovie(
                            3,
                            listOf(
                                LocalTime.of(13, 0),
                                LocalTime.of(15, 0),
                                LocalTime.of(17, 0),
                                LocalTime.of(19, 0),
                            ),
                        ),
                    ),
            ),
            Theater(
                id = 4,
                name = "아주아주아주아주아주긴 극장 이름인데 아주 나이스 빰빠밤바밤 밤밤 빠라바라밤 바밤",
                screeningMovies =
                    listOf(
                        ScreeningMovie(
                            4,
                            listOf(
                                LocalTime.of(13, 0),
                                LocalTime.of(15, 0),
                                LocalTime.of(17, 0),
                                LocalTime.of(19, 0),
                            ),
                        ),
                    ),
            ),
        )

    override fun findTheaterList(movieId: Long): List<Theater> {
        return list.filter { theater ->
            /*theater.screeningMovies.any { screen ->
                screen.movieId == movieId*/
            theater.findScreenScheduleListWithMovieId(movieId).isNotEmpty()
        }
    }

    override fun findTheaterOrNull(theaterId: Long): Theater? {
        return list.find { theater ->
            theater.id == theaterId
        }
    }
}
