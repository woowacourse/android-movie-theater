package woowacourse.movie.presentation.ticket

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.jupiter.api.Test
import woowacourse.movie.R
import woowacourse.movie.fixture.BOOKED_TICKET
import woowacourse.movie.presentation.ticket.list.TicketListFragment

@Suppress("ktlint:standard:function-naming")
class TicketListFragmentTest {
    private lateinit var fragmentScenario: FragmentScenario<TicketListFragment>

    @Test
    fun 티켓_목록이_출력된다() {
        // given
        fragmentScenario = launchFragmentInContainer()
        fragmentScenario.onFragment { fragment ->
            fragment.showTicketList(listOf(BOOKED_TICKET))
        }

        // when
        onView(withId(R.id.recyclerview_tickets))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        // then
        onView(withText(BOOKED_TICKET.movie.title)).check(matches(isDisplayed()))
    }
}
