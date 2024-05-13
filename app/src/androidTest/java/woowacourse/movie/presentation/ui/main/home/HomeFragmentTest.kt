package woowacourse.movie.presentation.ui.main.home

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.presentation.ui.main.MainActivity

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.navigateToBottomMenu(
                R.id.fc_main,
                HomeFragment(),
            )
        }
    }

    @Test
    fun `영화_목록을_보여준다`() {
        onView(withId(R.id.rv_screen)).check(matches(isDisplayed()))
    }

    @SuppressLint("ResourceType")
    fun FragmentManager.navigateToBottomMenu(
        @LayoutRes
        fragmentLayoutResource: Int,
        nextFragment: Fragment,
    ) {
        commit {
            setReorderingAllowed(true)
            replace(fragmentLayoutResource, nextFragment)
        }
    }
}
