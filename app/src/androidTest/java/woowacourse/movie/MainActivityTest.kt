package woowacourse.movie

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.view.MainActivity

class MainActivityTest {
    @get:Rule
    val activityRule =
        ActivityScenarioRule<MainActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                MainActivity::class.java,
            ),
        )

    @Test
    fun `메인_액티비티의_첫_화면은_홈이다`() {
        onView(withId(R.id.recycler_view_home_screening_movies))
            .check(matches(isDisplayed()))
    }
}
