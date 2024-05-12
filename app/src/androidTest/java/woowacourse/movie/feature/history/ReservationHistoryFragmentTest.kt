package woowacourse.movie.feature.history

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.RoomTicketRepository
import woowacourse.movie.feature.history.adapter.ReservationHistoryViewHolder
import woowacourse.movie.feature.movieId
import woowacourse.movie.feature.screeningDate
import woowacourse.movie.feature.screeningTime
import woowacourse.movie.feature.selectedSeats
import woowacourse.movie.feature.theaterName

@RunWith(AndroidJUnit4::class)
class ReservationHistoryFragmentTest {
    private val ticketRepository =
        RoomTicketRepository(
            TicketDatabase.instance(ApplicationProvider.getApplicationContext()).ticketDao(),
        )

    @Before
    fun setUp() {
        ticketRepository.save(movieId, screeningDate, screeningTime, selectedSeats, theaterName)
        launchFragmentInContainer<ReservationHistoryFragment>()
    }

    @After
    fun tearDown() {
        ticketRepository.deleteAll()
    }

    @Test
    fun `예매된_티켓_정보를_보여준다`() {
        onView(withId(R.id.reservation_history_recycler_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `예매된_티켓의_영화_제목을_보여준다`() {
        onView(withId(R.id.reservation_history_recycler_view)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(ReservationHistoryViewHolder::class.java),
            ).atPosition(0),
        )

        onView(withText("타이타닉 0"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `예매된_티켓의_상영_날짜를_보여준다`() {
        onView(withId(R.id.reservation_history_recycler_view)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(ReservationHistoryViewHolder::class.java),
            ).atPosition(0),
        )

        onView(withText("2024.4.1"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `예매된_티켓의_상영_시간을_보여준다`() {
        onView(withId(R.id.reservation_history_recycler_view)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(ReservationHistoryViewHolder::class.java),
            ).atPosition(0),
        )

        onView(withText("12:30"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `예매된_티켓의_극장명을_보여준다`() {
        onView(withId(R.id.reservation_history_recycler_view)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(ReservationHistoryViewHolder::class.java),
            ).atPosition(0),
        )

        onView(withText("선릉 극장"))
            .check(matches(isDisplayed()))
    }
}
