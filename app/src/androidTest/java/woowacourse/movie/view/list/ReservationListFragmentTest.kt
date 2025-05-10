package woowacourse.movie.view.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.fixture.TestData
import woowacourse.movie.matchers.RecyclerViewMatcher.Companion.withRecyclerView
import woowacourse.movie.matchers.isDisplayed
import woowacourse.movie.matchers.matchText
import woowacourse.movie.matchers.performClick
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.reservelist.ReservationListFragment

@RunWith(AndroidJUnit4::class)
@Suppress("FunctionName")
class ReservationListFragmentTest {
    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.reservation_list))
            .performClick()
        scenarioRule.scenario.onActivity { activity ->
            val fragment =
                activity.supportFragmentManager.findFragmentById(
                    R.id.fragment_container_main,
                )
            (fragment as ReservationListFragment).showReservationList(TestData.tickets)
        }
    }

    @Test
    fun 예매_목록_요소를_누르면_예매_결과_화면으로_이동한다() {
        // when
        onView(
            withRecyclerView(R.id.lv_reservation_list)
                .atPositionOnView(0, R.id.item_reservation_list),
        )
            .performClick()

        // then
        onView(withId(R.id.activity_reservation_result))
            .isDisplayed()
        onView(withId(R.id.tv_movie_title))
            .matchText("해리 포터와 마법사의 돌")
        onView(withId(R.id.tv_movie_date))
            .matchText("2025.5.1 09:00")
        onView(withId(R.id.tv_reservation_count_info))
            .matchText("일반 2명")
        onView(withId(R.id.tv_reservation_seats))
            .matchText("|A2|")
        onView(withId(R.id.tv_reservation_cinema))
            .matchText("잠실 극장")
    }
}
