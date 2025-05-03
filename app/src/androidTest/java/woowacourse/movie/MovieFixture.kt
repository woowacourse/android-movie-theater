package woowacourse.movie

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
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

fun ViewInteraction.performClick() = this.perform(click())
fun ViewInteraction.checkIsDisplayed() = this.check(matches(isDisplayed()))

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

    val BOOKING_STATUS =
        BookingStatus(MOVIE, true, BookingSeats(2), LocalDateTime.of(2025, 4, 30, 9, 0, 0))

    val THEATER_MOVIES =
        Movies(
            mapOf(
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
            ),
        )

    val THEATER_TIMETABLE =
        mapOf(
            Title("해리포터와 마법사의 돌") to listOf(LocalTime.of(10, 0)),
        )

    val THEATER = Theater("선릉", THEATER_MOVIES, THEATER_TIMETABLE)
}
