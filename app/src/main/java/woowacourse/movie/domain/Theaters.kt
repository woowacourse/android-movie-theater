package woowacourse.movie.domain

import java.time.LocalTime

class Theaters(
    val values: List<Theater>,
) {
    fun filterByMovie(movie: Movie): List<Theater> {
        return values.filter { theater -> theater.isShowing(movie) }
    }

    companion object {
        val theaters =
            listOf(
                Theater(
                    "선릉",
                    Movies.seolleungMovies,
                    mapOf(
                        Title("해리포터와 불의 잔") to
                            listOf(
                                LocalTime.of(13, 0),
                                LocalTime.of(15, 0),
                                LocalTime.of(17, 0),
                                LocalTime.of(19, 0),
                            ),
                        Title("해리포터와 아즈카반의 죄수") to
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(15, 0),
                            ),
                    ),
                ),
                Theater(
                    "잠실",
                    Movies.jamsilMovies,
                    mapOf(
                        Title("해리포터와 마법사의 돌") to
                            listOf(
                                LocalTime.of(10, 0),
                                LocalTime.of(12, 0),
                                LocalTime.of(14, 0),
                            ),
                        Title("해리포터와 아즈카반의 죄수") to
                            listOf(
                                LocalTime.of(9, 0),
                            ),
                        Title("해리포터와 비밀의 방") to
                            listOf(
                                LocalTime.of(11, 0),
                                LocalTime.of(13, 0),
                            ),
                    ),
                ),
                Theater(
                    "강남",
                    Movies.gangnamMovies,
                    mapOf(
                        Title("해리포터와 불의 잔") to
                            listOf(
                                LocalTime.of(13, 0),
                                LocalTime.of(16, 0),
                            ),
                        Title("해리포터와 아즈카반의 죄수") to
                            listOf(
                                LocalTime.of(10, 30),
                                LocalTime.of(18, 0),
                            ),
                    ),
                ),
            )
    }
}
