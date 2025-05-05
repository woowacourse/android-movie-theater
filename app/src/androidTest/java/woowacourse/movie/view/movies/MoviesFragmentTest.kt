package woowacourse.movie.view.movies

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.fixture.TestData
import woowacourse.movie.view.matchers.RecyclerViewMatcher.Companion.withRecyclerView
import woowacourse.movie.view.matchers.scrollToPosition

@RunWith(AndroidJUnit4::class)
@Suppress("FunctionName")
class MoviesFragmentTest {
    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.home))
            .perform(click())
    }

    @Test
    fun `영화_정보가_표시된다`() {
        onView(withId(R.id.movies))
            .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.lv_movie).atPositionOnView(0, R.id.tv_title))
            .check(matches(withText("해리 포터와 마법사의 돌")))
        onView(withRecyclerView(R.id.lv_movie).atPositionOnView(0, R.id.tv_date))
            .check(matches(withText("상영일: 2025.5.1 ~ 2025.5.30")))
        onView(withRecyclerView(R.id.lv_movie).atPositionOnView(0, R.id.tv_running_time))
            .check(matches(withText("러닝타임: 152분")))
        onView(withRecyclerView(R.id.lv_movie).atPositionOnView(0, R.id.btn_reservation))
            .check(matches(withText("지금 예매")))
    }

    @Test
    fun 극장_선택_창에는_극장의_이름과_상영_가능_시간이_나타난다() {
        // given, when
        onView(withRecyclerView(R.id.lv_movie).atPositionOnView(0, R.id.btn_reservation))
            .perform(click())

        // then
        onView(withId(R.id.lv_cinema))
            .check(matches(isDisplayed()))
        onView(withRecyclerView(R.id.lv_cinema).atPositionOnView(0, R.id.tv_cinema))
            .check(matches(withText("선릉 극장")))
        onView(withRecyclerView(R.id.lv_cinema).atPositionOnView(0, R.id.tv_screening_time))
            .check(matches(withText("2개의 상영 시간")))
    }

    @Test
    fun 극장_선택_창의_요소를_누르면_예매_화면으로_넘어간다() {
        // given
        onView(withRecyclerView(R.id.lv_movie).atPositionOnView(0, R.id.btn_reservation))
            .perform(click())

        // when
        onView(withRecyclerView(R.id.lv_cinema).atPositionOnView(0, -1))
            .perform(click())

        // then
        onView(withId(R.id.reservation))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 영화목록에_영화가_세_번_노출될_때마다_광고가_한_번_노출된다() {
        (0..8).forEach {
            if ((it + 1) % 4 == 0) {
                onView(withId(R.id.lv_movie))
                    .perform(scrollToPosition(it))
                onView(withRecyclerView(R.id.lv_movie).atPositionOnView(it, R.id.item_advertisement))
                    .check(matches(isDisplayed()))
            } else {
                onView(withId(R.id.lv_movie))
                    .perform(scrollToPosition(it))
                onView(withRecyclerView(R.id.lv_movie).atPositionOnView(it, R.id.item_movie))
                    .check(matches(isDisplayed()))
            }
        }
    }

    @Test
    fun 영화_목록의_요소는_10_000개까지_추가될_수_있다() {
        scenarioRule.scenario.onActivity {
            (it.selectedFragment as MoviesFragment).showMovies(
                TestData.movies,
            )
            val recyclerView = it.findViewById<RecyclerView>(R.id.lv_movie)
            assertThat(recyclerView.adapter?.itemCount).isEqualTo(10_000)
        }
    }
}
