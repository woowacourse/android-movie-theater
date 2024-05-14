package woowacourse.movie.ui.booking

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.FakeUserTicketDao
import woowacourse.movie.R
import woowacourse.movie.model.db.UserTicket
import woowacourse.movie.model.db.UserTicketRepositoryImpl
import woowacourse.movie.model.movie.Seat
import woowacourse.movie.model.movie.SeatRow
import woowacourse.movie.ui.booking.adapter.MovieBookingHistoryViewHolder
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class MovieBookingHistoryFragmentTest {
    @Before
    fun setUp() {
        UserTicketRepositoryImpl.get(FakeUserTicketDao()).apply {
            insert(testUserTicket)
        }
        launchFragmentInContainer<MovieBookingHistoryFragment>()
    }

    @Test
    fun `화면이_띄워지면_영화_목록이_보인다`() {
        Espresso.onView(withId(R.id.booked_movie_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `화면이_띄워지면_영화_제목이_보인다`() {
        Espresso.onView(withId(R.id.booked_movie_list))
            .perform(RecyclerViewActions.scrollToPosition<MovieBookingHistoryViewHolder>(0))
            .check(
                matches(
                    hasDescendant(
                        allOf(
                            withText(testUserTicket.movieTitle),
                            isDisplayed(),
                        ),
                    ),
                ),
            )
    }

    @Test
    fun `화면이_띄워지면_상영일이_보인다`() {
        Espresso.onView(withId(R.id.booked_movie_list))
            .perform(RecyclerViewActions.scrollToPosition<MovieBookingHistoryViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText("2024-03-28"), isDisplayed()))))
    }

    @Test
    fun `화면이_띄워지면_상영시간이_보인다`() {
        Espresso.onView(withId(R.id.booked_movie_list))
            .perform(RecyclerViewActions.scrollToPosition<MovieBookingHistoryViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText("21:00"), isDisplayed()))))
    }

    @Test
    fun `화면이_띄워지면_극장_이름이_보인다`() {
        Espresso.onView(withId(R.id.booked_movie_list))
            .perform(RecyclerViewActions.scrollToPosition<MovieBookingHistoryViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText("선릉 극장"), isDisplayed()))))
    }

    companion object {
        private val testUserTicket =
            UserTicket(
                movieTitle = "해리포터와 마법사의 돌돌이",
                screeningStartDateTime = LocalDateTime.of(2024, 3, 28, 21, 0),
                reservationCount = 1,
                reservationSeats = listOf(Seat(SeatRow.A, 0)),
                theaterName = "선릉",
                reservationAmount = 10000,
                id = 1L,
            )
    }
}
