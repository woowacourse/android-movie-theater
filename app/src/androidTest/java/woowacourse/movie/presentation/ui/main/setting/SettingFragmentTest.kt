package woowacourse.movie.presentation.ui.main.setting

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.presentation.ui.main.MainActivity

@RunWith(AndroidJUnit4::class)
class SettingFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.POST_NOTIFICATIONS)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.navigateToBottomMenu(
                R.id.fc_main,
                SettingFragment(),
            )
        }
    }

    @Test
    fun `푸쉬_알림_수신_설정을_위한_스위치를_보여준다`() {
        Espresso.onView(ViewMatchers.withId(R.id.switch_setting))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `푸쉬_알림_수신_설정_텍스트를_보여준다`() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_push_alarm_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.tv_push_alarm_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
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
