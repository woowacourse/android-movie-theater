package woowacourse.movie.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.switchmaterial.SwitchMaterial
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.view.moviemain.setting.SettingFragment

@RunWith(AndroidJUnit4::class)
class SettingFragmentTest {
    private val scenario = launchFragmentInContainer<SettingFragment>(themeResId = R.style.Theme_Movie)

    @Test
    fun 토글의_설정값이_유지된다() {
        onView(ViewMatchers.withId(R.id.setting_toggle)).perform(click())
        var isChecked = false
        scenario.onFragment {
            isChecked = it.requireView().findViewById<SwitchMaterial>(R.id.setting_toggle).isChecked
        }
        scenario.recreate()
        if (isChecked) {
            onView(ViewMatchers.withId(R.id.setting_toggle))
                .check(ViewAssertions.matches(ViewMatchers.isChecked())).perform(click())
        } else {
            onView(ViewMatchers.withId(R.id.setting_toggle))
                .check(ViewAssertions.matches(ViewMatchers.isNotChecked())).perform(click())
        }
    }
}
