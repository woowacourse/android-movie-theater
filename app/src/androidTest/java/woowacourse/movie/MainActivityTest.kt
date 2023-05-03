package woowacourse.movie

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.ui.activity.MainActivity

class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun 네_번째_요소를_누르면_우아한테크코스_사이트로_이동한다() {
        val position = 3 // 0 부터 시작
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(position, click()))

        intended(hasData(Uri.parse("https://woowacourse.github.io/")))
    }

    @Test
    fun 예매_내역_메뉴를_클릭하면_예매_내역_프래그먼트로_화면이_전환된다() {
        onView(withId(R.id.bottom_item_list))
            .perform(click())

        onView(withId(R.id.fragment_reservation_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 홈_메뉴를_클릭하면_홈_프래그먼트로_화면이_전환된다() {
        onView(withId(R.id.bottom_item_home))
            .perform(click())

        onView(withId(R.id.fragment_movie_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 설정_메뉴를_클릭하면_설정_프래그먼트로_화면이_전환된다() {
        onView(withId(R.id.bottom_item_settings))
            .perform(click())

        onView(withId(R.id.fragment_settings))
            .check(matches(isDisplayed()))
    }
}
