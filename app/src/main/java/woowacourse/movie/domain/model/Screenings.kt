package woowacourse.movie.domain.model

import woowacourse.movie.domain.model.Movie.Companion.movie0
import woowacourse.movie.domain.model.Movie.Companion.movie1
import woowacourse.movie.domain.model.Movie.Companion.movie2
import woowacourse.movie.domain.model.Movie.Companion.movie3
import woowacourse.movie.domain.model.Movie.Companion.movie4

@JvmInline
value class Screenings(
    val value: List<Screening>,
) {
    fun getMovieScreenings(movieTitle: String): Screenings =
        Screenings(
            value.filter { screening ->
                screening.movie.title == movieTitle
            },
        )

    companion object {
        private const val THEATER_NAME_0: String = "CGV명동"
        private const val THEATER_NAME_1: String = "CGV동대문"
        private const val THEATER_NAME_2: String = "CGV청담씨네시티"
        private const val THEATER_NAME_3: String = "CGV명동역 씨네라이브러리"
        private const val THEATER_NAME_4: String = "CINE de CHEF 용산아이파크몰"

        val screenings =
            Screenings(
                listOf(
                    Screening(
                        movie0,
                        THEATER_NAME_0,
                        listOf(MovieTime(9, 0), MovieTime(12, 0), MovieTime(15, 0)),
                    ),
                    Screening(
                        movie0,
                        THEATER_NAME_1,
                        listOf(MovieTime(10, 0), MovieTime(13, 0)),
                    ),
                    Screening(
                        movie0,
                        THEATER_NAME_2,
                        listOf(
                            MovieTime(11, 0),
                            MovieTime(14, 0),
                            MovieTime(17, 0),
                            MovieTime(20, 0),
                        ),
                    ),
                    Screening(
                        movie1,
                        THEATER_NAME_3,
                        listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0)),
                    ),
                    Screening(
                        movie1,
                        THEATER_NAME_4,
                        listOf(MovieTime(10, 0), MovieTime(12, 0), MovieTime(18, 0)),
                    ),
                    Screening(
                        movie2,
                        THEATER_NAME_2,
                        listOf(MovieTime(9, 0), MovieTime(11, 0)),
                    ),
                    Screening(
                        movie2,
                        THEATER_NAME_3,
                        listOf(MovieTime(13, 0), MovieTime(15, 0)),
                    ),
                    Screening(
                        movie2,
                        THEATER_NAME_4,
                        listOf(MovieTime(17, 0), MovieTime(19, 0)),
                    ),
                    Screening(
                        movie2,
                        THEATER_NAME_0,
                        listOf(MovieTime(21, 0)),
                    ),
                    Screening(
                        movie3,
                        THEATER_NAME_4,
                        listOf(MovieTime(10, 0), MovieTime(13, 0)),
                    ),
                    Screening(
                        movie3,
                        THEATER_NAME_1,
                        listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0)),
                    ),
                    Screening(
                        movie3,
                        THEATER_NAME_3,
                        listOf(MovieTime(15, 0), MovieTime(18, 0)),
                    ),
                    Screening(
                        movie4,
                        THEATER_NAME_2,
                        listOf(MovieTime(12, 0), MovieTime(15, 0), MovieTime(18, 0)),
                    ),
                    Screening(
                        movie4,
                        THEATER_NAME_0,
                        listOf(MovieTime(10, 0), MovieTime(13, 0)),
                    ),
                ),
            )
    }
}
