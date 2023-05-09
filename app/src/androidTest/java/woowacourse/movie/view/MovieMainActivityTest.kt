package woowacourse.movie.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.view.moviemain.MovieMainActivity
import woowacourse.movie.view.moviemain.movielist.MovieListFragment
import woowacourse.movie.view.moviemain.reservationlist.ReservationListFragment
import woowacourse.movie.view.moviemain.setting.SettingFragment

@RunWith(AndroidJUnit4::class)
class MovieMainActivityTest {
    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(MovieMainActivity::class.java)

    @Test
    fun 예매내역_버튼을_누르면_예매내역_Fragment로_바뀐다() {
        onView(withId(R.id.action_reservation_list)).perform(click())
        mActivityTestRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentByTag(ReservationListFragment.TAG_RESERVATION_LIST)
            assertTrue(fragment != null && fragment.isVisible)
        }
    }

    @Test
    fun 홈_버튼을_누르면_홈_Fragment로_바뀐다() {
        onView(withId(R.id.action_home)).perform(click())
        mActivityTestRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentByTag(MovieListFragment.TAG_MOVIE_LIST)
            assertTrue(fragment != null && fragment.isVisible)
        }
    }

    @Test
    fun 설정_버튼을_누르면_설정_Fragment로_바뀐다() {
        onView(withId(R.id.action_setting)).perform(click())
        mActivityTestRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentByTag(SettingFragment.TAG_SETTING)
            assertTrue(fragment != null && fragment.isVisible)
        }
    }
}
