package woowacourse.movie

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.LayoutMatchers.hasEllipsizedText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Seat
import woowacourse.movie.presentation.ticketingResult.TicketingResultActivity

@RunWith(AndroidJUnit4::class)
class TicketingResultActivityTest {
    private val movieReservation =
        Reservation(
            movieTitle = "해리 포터와 마법사의 돌",
            screeningDateTime = "2024-04-30",
            selectedSeats =
                listOf(
                    Seat(1, 1),
                    Seat(3, 2),
                ),
            theaterName = "아주아주아주아주아주긴 극장 이름인데 아주 나이스 빰빠밤바밤 밤밤 빠라바라밤 바밤",
        )

    @get:Rule
    val activityScenario: ActivityScenarioRule<TicketingResultActivity> =
        ActivityScenarioRule<TicketingResultActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                TicketingResultActivity::class.java,
            ).apply
                {
                    putExtra(TicketingResultActivity.EXTRA_MOVIE_TICKET, movieReservation)
                },
        )

    @Test
    fun `예매_결과_화면_컴포넌트를_보여준다`() {
        onView(withId(R.id.tv_guide)).check(matches(withText(R.string.text_guide)))
        onView(withId(R.id.tv_movie_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_ticket_count)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_price)).check(matches(isDisplayed()))
    }

    @Test
    fun `극장_이름이_너무_긴_경우_말줄임표로_표시된다`() {
        onView(withId(R.id.tv_theater_name))
            .check(matches(hasEllipsizedText()))
    }
}
