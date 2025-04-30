package woowacourse.movie.domain.model

import woowacourse.movie.domain.model.Movie.Companion.movie0
import woowacourse.movie.domain.model.Movie.Companion.movie1
import woowacourse.movie.domain.model.Movie.Companion.movie2
import woowacourse.movie.domain.model.Movie.Companion.movie3
import woowacourse.movie.domain.model.Movie.Companion.movie4
import woowacourse.movie.domain.model.Theater.Companion.theater0
import woowacourse.movie.domain.model.Theater.Companion.theater1
import woowacourse.movie.domain.model.Theater.Companion.theater2
import woowacourse.movie.domain.model.Theater.Companion.theater3
import woowacourse.movie.domain.model.Theater.Companion.theater4

@JvmInline
value class Screenings(
    val value: List<Screening>,
) {
    fun getMovieScreenings(movieTitle: String): Screenings =
        Screenings(
            screenings.value.filter { screening ->
                screening.movie.title == movieTitle
            },
        )

    companion object {
        val screenings =
            Screenings(
                listOf(
                    Screening(
                        movie0,
                        theater0,
                        listOf(MovieTime(9, 0), MovieTime(12, 0), MovieTime(15, 0)),
                    ),
                    Screening(
                        movie0,
                        theater1,
                        listOf(MovieTime(10, 0), MovieTime(13, 0)),
                    ),
                    Screening(
                        movie0,
                        theater2,
                        listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0), MovieTime(20, 0)),
                    ),
                    Screening(
                        movie1,
                        theater3,
                        listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0)),
                    ),
                    Screening(
                        movie1,
                        theater4,
                        listOf(MovieTime(10, 0), MovieTime(12, 0), MovieTime(18, 0)),
                    ),
                    Screening(
                        movie2,
                        theater2,
                        listOf(MovieTime(9, 0), MovieTime(11, 0)),
                    ),
                    Screening(
                        movie2,
                        theater3,
                        listOf(MovieTime(13, 0), MovieTime(15, 0)),
                    ),
                    Screening(
                        movie2,
                        theater4,
                        listOf(MovieTime(17, 0), MovieTime(19, 0)),
                    ),
                    Screening(
                        movie2,
                        theater0,
                        listOf(MovieTime(21, 0)),
                    ),
                    Screening(
                        movie3,
                        theater4,
                        listOf(MovieTime(10, 0), MovieTime(13, 0)),
                    ),
                    Screening(
                        movie3,
                        theater1,
                        listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0)),
                    ),
                    Screening(
                        movie3,
                        theater3,
                        listOf(MovieTime(15, 0), MovieTime(18, 0)),
                    ),
                    Screening(
                        movie4,
                        theater2,
                        listOf(MovieTime(12, 0), MovieTime(15, 0), MovieTime(18, 0)),
                    ),
                    Screening(
                        movie4,
                        theater0,
                        listOf(MovieTime(10, 0), MovieTime(13, 0)),
                    ),
                ),
            )
    }
}
