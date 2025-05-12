package woowacourse.movie.view.history

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.ext.RecyclerViewMatchers
import woowacourse.movie.ext.isTextMatches
import woowacourse.movie.fixture.ticketFixtures
import woowacourse.movie.view.history.adapter.model.toItem
import woowacourse.movie.view.main.MainActivity

@RunWith(AndroidJUnit4::class)
class BookingHistoryFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            android.Manifest.permission.POST_NOTIFICATIONS,
        )

    @Before
    fun setup() {
        onView(withId(R.id.action_history)).perform(click())

        activityRule.scenario.onActivity { activity ->
            val fragment =
                activity.supportFragmentManager.findFragmentById(
                    R.id.fragment_container_view,
                )
            (fragment as BookingHistoryFragment).showTickets(ticketFixtures)
        }
    }

    @Test
    fun `예매_내역_목록에_전달된_티켓들이_모두_표시된다`() {
        ticketFixtures.forEachIndexed { index, ticket ->
            val ticketItem = ticket.toItem()

            // 영화 제목
            onView(
                RecyclerViewMatchers.atPositionOnView(
                    index,
                    R.id.tv_movie_name,
                ),
            ).isTextMatches(ticketItem.movieName)

            // 예매 날짜
            onView(
                RecyclerViewMatchers.atPositionOnView(
                    index,
                    R.id.tv_date,
                ),
            ).isTextMatches(ticketItem.bookingDate)

            // 예매 날짜
            onView(
                RecyclerViewMatchers.atPositionOnView(
                    index,
                    R.id.tv_date,
                ),
            ).isTextMatches(ticketItem.bookingDate)

            // 예매 시간
            onView(
                RecyclerViewMatchers.atPositionOnView(
                    index,
                    R.id.tv_time,
                ),
            ).isTextMatches(ticketItem.bookingTime)

            // 극장 이름
            onView(
                RecyclerViewMatchers.atPositionOnView(
                    index,
                    R.id.tv_theater_name,
                ),
            ).isTextMatches(ticketItem.theaterName)
        }
    }
}
