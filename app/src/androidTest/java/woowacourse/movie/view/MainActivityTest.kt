package woowacourse.movie.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.matchers.isDisplayed
import woowacourse.movie.matchers.performClick

@RunWith(AndroidJUnit4::class)
@Suppress("FunctionName")
class MainActivityTest {
    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun 홈_버튼을_누르면_영화_목록이_표시된다() {
        onView(withId(R.id.home))
            .performClick()
        onView(withId(R.id.movies))
            .isDisplayed()
    }

    @Test
    fun 예매_내역_버튼을_누르면_예매_내역이_표시된다() {
        onView(withId(R.id.reservation_list))
            .performClick()
        onView(withId(R.id.reservation_list_fragment))
            .isDisplayed()
    }

    @Test
    fun 설정_버튼을_누르면_설정_화면이_표시된다() {
        onView(withId(R.id.settings))
            .performClick()
        onView(withId(R.id.settings_fragment))
            .isDisplayed()
    }
}
