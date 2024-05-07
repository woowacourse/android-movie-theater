package woowacourse.movie.view.setting

import android.content.Context
import android.content.SharedPreferences
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.utils.MovieUtils.navigateToBottomMenu
import woowacourse.movie.view.MainActivity

@RunWith(AndroidJUnit4::class)
@MediumTest
class SettingFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            sharedPreferences = it.getSharedPreferences(
                SettingFragment.PUSH_SETTING,
                Context.MODE_PRIVATE
            )
            it.supportFragmentManager.navigateToBottomMenu(
                R.id.fragment_container_main,
                SettingFragment(),
            )
        }
    }

    @Test
    fun `기존_설정값이_on으로_되어있다면_스위치_버튼이_활성화_되어_있어야_한다`() {

        // When
        sharedPreferences.edit().putBoolean(SettingFragment.PUSH_SETTING,true).commit()
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            it.supportFragmentManager.navigateToBottomMenu(
                R.id.fragment_container_main,
                SettingFragment(),
            )
        }

        // Then
        onView(withId(R.id.switch_button)).check(matches(isChecked()))
    }

    @Test
    fun `기존_설정값이_off로_되어있다면_스위치_버튼이_비활성화_되어_있어야_한다`() {

        // When
        sharedPreferences.edit().putBoolean(SettingFragment.PUSH_SETTING,false).commit()
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            it.supportFragmentManager.navigateToBottomMenu(
                R.id.fragment_container_main,
                SettingFragment(),
            )
        }

        // Then
        onView(withId(R.id.switch_button)).check(matches(isNotChecked()))
    }

    @Test
    fun `스위치_버튼이_활성화_상태일_때_스위치_버튼을_클릭하면_활성화되어야_한다`() {
        // When
        sharedPreferences.edit().putBoolean(SettingFragment.PUSH_SETTING,false).commit()
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            it.supportFragmentManager.navigateToBottomMenu(
                R.id.fragment_container_main,
                SettingFragment(),
            )
        }
        onView(withId(R.id.switch_button)).perform(click())

        // Then
        onView(withId(R.id.switch_button)).check(matches(isChecked()))
    }

    @Test
    fun `스위치_버튼이_비활성화_상태일_때_스위치_버튼을_클릭하면_비활성화되어야_한다`() {
        // When
        sharedPreferences.edit().putBoolean(SettingFragment.PUSH_SETTING,true).commit()
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            it.supportFragmentManager.navigateToBottomMenu(
                R.id.fragment_container_main,
                SettingFragment(),
            )
        }
        onView(withId(R.id.switch_button)).perform(click())

        // Then
        onView(withId(R.id.switch_button)).check(matches(isNotChecked()))
    }
}
