//package woowacourse.movie.presentation.view.main.setting
//
//import androidx.appcompat.widget.SwitchCompat
//import androidx.fragment.app.testing.launchFragment
//import androidx.fragment.app.testing.launchFragmentInContainer
//import androidx.fragment.app.testing.withFragment
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.ViewAction
//import androidx.test.espresso.ViewAssertion
//import androidx.test.espresso.action.ViewActions
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.isChecked
//import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import org.junit.Test
//import org.junit.runner.RunWith
//import woowacourse.movie.R
//
//@RunWith(AndroidJUnit4::class)
//class SettingFragmentTest{
//
//    @Test
//    fun OFF일떄_스위치를_클릭하면_ON으로_변경된다() {
//        val scenario = launchFragment<SettingFragment>().withFragment {
//            this.view!!.findViewById<SwitchCompat>(R.id.switch_setting_alarm).isSelected = false
//        }
//        onView(withId(R.id.switch_setting_alarm)).perform(click()).check(matches(isChecked()))
//    }
//}