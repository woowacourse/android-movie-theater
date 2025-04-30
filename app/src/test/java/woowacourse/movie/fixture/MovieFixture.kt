package woowacourse.movie.fixture

import woowacourse.movie.R
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Movies
import woowacourse.movie.domain.ScreeningPeriod
import woowacourse.movie.domain.Title
import woowacourse.movie.domain.seat.BookingSeats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object MovieFixture {
    val movie =
        Movie(
            Title("해리포터와 마법사의 돌"),
            R.drawable.movie_poster,
            ScreeningPeriod(
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 4, 25),
            ),
            152,
        )

    val dates =
        listOf(
            LocalDate.of(2025, 4, 1),
            LocalDate.of(2025, 4, 2),
            LocalDate.of(2025, 4, 3),
            LocalDate.of(2025, 4, 4),
            LocalDate.of(2025, 4, 5),
        )

    val dates2 =
        listOf(
            LocalDate.of(2025, 4, 3),
            LocalDate.of(2025, 4, 4),
            LocalDate.of(2025, 4, 5),
        )

    val dates3 =
        listOf(
            LocalDate.of(2025, 4, 5),
        )

    val movies =
        mapOf<Title, Movie>(
            Title("해리포터와 마법사의 돌") to
                Movie(
                    Title("해리포터와 마법사의 돌"),
                    R.drawable.movie_poster,
                    ScreeningPeriod(
                        LocalDate.of(2025, 4, 1),
                        LocalDate.of(2025, 4, 25),
                    ),
                    152,
                ),
            Title("포니") to
                Movie(
                    Title("포니"),
                    R.drawable.movie_poster,
                    ScreeningPeriod(
                        LocalDate.of(2025, 4, 1),
                        LocalDate.of(2025, 4, 25),
                    ),
                    152,
                ),
        )

    val movies2 =
        mapOf<Title, Movie>(
            Title("해리포터와 마법사") to
                Movie(
                    Title("해리포터와 마법사의 돌"),
                    R.drawable.movie_poster,
                    ScreeningPeriod(
                        LocalDate.of(2025, 4, 1),
                        LocalDate.of(2025, 4, 25),
                    ),
                    152,
                ),
        )

    val listMovies =
        listOf<Movie>(
            Movie(
                Title("해리포터와 마법사의 돌"),
                R.drawable.movie_poster,
                ScreeningPeriod(
                    LocalDate.of(2025, 4, 1),
                    LocalDate.of(2025, 4, 25),
                ),
                152,
            ),
            Movie(
                Title("포니"),
                R.drawable.movie_poster,
                ScreeningPeriod(
                    LocalDate.of(2025, 4, 1),
                    LocalDate.of(2025, 4, 25),
                ),
                152,
            ),
        )

    val MAX_MOVIES =
        Movies(
            (0 until 10_000).associate { index ->
                Title("해리포터와 마법사의 돌 $index") to
                    Movie(
                        Title("해리포터와 마법사의 돌 $index"),
                        R.drawable.movie_poster,
                        ScreeningPeriod(
                            LocalDate.of(2025, 4, 1),
                            LocalDate.of(2025, 4, 25),
                        ),
                        152,
                    )
            },
        )

    val THEATER_MOVIES =
        Movies(
            mapOf(Title("해리포터와 마법사의 돌") to
                        Movie(
                            Title("해리포터와 마법사의 돌"),
                            R.drawable.movie_poster,
                            ScreeningPeriod(
                                LocalDate.of(2025, 4, 1),
                                LocalDate.of(2025, 4, 25),
                            ),
                            152,
                        )
            )
        )

    val THEATER_TIMETABLE = mapOf(
        Title("해리포터와 마법사의 돌") to listOf(LocalTime.of(10,0))
    )

    val THEATER_MOVIE = Movie(
        Title("해리포터와 마법사의 돌"),
        R.drawable.movie_poster,
        ScreeningPeriod(
            LocalDate.of(2025, 4, 1),
            LocalDate.of(2025, 4, 25),
        ),
        152,
    )

    val THEATER_MOVIE_IS_NOT = Movie(
        Title("해리포터"),
        R.drawable.movie_poster,
        ScreeningPeriod(
            LocalDate.of(2025, 4, 1),
            LocalDate.of(2025, 4, 25),
        ),
        152,
    )

    val BOOKING_STATUS =
        BookingStatus(movie, true, BookingSeats(2), LocalDateTime.of(2025, 4, 30, 9, 0, 0))
}
