package woowacourse.movie

import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Movies
import woowacourse.movie.domain.ScreeningPeriod
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.Title
import woowacourse.movie.domain.seat.BookingSeats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object MovieFixture {
    const val HARRY_POTTER_TITLE = "해리포터와 마법사의 돌"
    const val HARRY_POTTER_DATE = "상영일: 2025.04.01 ~ 2025.04.30"
    const val HARRY_POTTER_RUNNING_TIME = "러닝타임: 152분"
    const val BOOKING_DATETIME = "2025.04.30 09:00"
    const val BOOKING_TICKET_COUNT = "일반 2명"
    const val BOOKING_TICKET_PRICE = "26,000원 (현장 결제)"

    val MOVIE =
        Movie(
            Title("해리포터와 마법사의 돌"),
            R.drawable.movie_poster,
            ScreeningPeriod(
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 4, 30),
            ),
            152,
        )

    val MOVIES =
        Movies(
            mapOf(
                Title("해리포터와 불의 잔") to
                        Movie(
                            Title("해리포터와 불의 잔"),
                            R.drawable.harry_potter_and_the_goblet_of_fire,
                            ScreeningPeriod.ofDot("2025.06.01", "2025.06.30"),
                            157,
                        ),
                Title("해리포터와 아즈카반의 죄수") to
                        Movie(
                            Title("해리포터와 아즈카반의 죄수"),
                            R.drawable.harry_potter_and_the_prisoner_of_azkaban,
                            ScreeningPeriod.ofDot("2025.05.01", "2025.05.31"),
                            141,
                        ),
            ),
        )


    val BOOKING_STATUS =
        BookingStatus(MOVIE, true, BookingSeats(2), LocalDateTime.of(2025, 4, 30, 9, 0, 0))

    val THEATER = Theater(
        "선릉",
        MOVIES,
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
    )
}
