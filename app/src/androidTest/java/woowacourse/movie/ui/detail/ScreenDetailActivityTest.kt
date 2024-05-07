package woowacourse.movie.ui.detail

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R

@RunWith(AndroidJUnit4::class)
class ScreenDetailActivityTest {
    @get:Rule
    val activityRule: ActivityScenarioRule<ScreenDetailActivity> =
        ActivityScenarioRule<ScreenDetailActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                ScreenDetailActivity::class.java,
            ).apply {
                putExtra("screenId", 1)
            },
        )

    private val plusBtn: ViewInteraction = onView(withId(R.id.btn_plus))
    private val minusBtn: ViewInteraction = onView(withId(R.id.btn_minus))
    private val dateSpinner: ViewInteraction = onView(withId(R.id.spn_date))
    private val timeSpinner: ViewInteraction = onView(withId(R.id.spn_time))

    @Test
    fun `카운트가_10_미만일_때_플러스_버튼_눌러을_때_증가`() {
        // when
        plusBtn.perform(click())

        // then
        onView(withId(R.id.tv_count))
            .check(matches(withText("2")))
    }

    @Test
    fun `카운트가_1_초과일_때_마이너스_버튼_눌러을_때_감소`() {
        // given - 카운트가 2일 때
        plusBtn.perform(click())

        // when
        minusBtn.perform(click())

        // then
        onView(withId(R.id.tv_count))
            .check(matches(withText("1")))
    }

    @Test
    fun `카운트가_1일_때_마이너스_버튼_누르면_감소하지_않는다`() {
        // when
        minusBtn.perform(click())

        // then
        onView(withId(R.id.tv_count))
            .check(matches(withText("1")))
    }

    @Test
    fun `카운트가_10일_때_플러스_버튼_누르면_증가하지_않는다`() {
        // given - 카운트가 1 -> 10 일 때
        clickPlusButtonMultipleTimes(9)

        // when
        plusBtn.perform(click())

        // then
        onView(withId(R.id.tv_count))
            .check(matches(withText("10")))
    }

    @Test
    fun `카운트가_2일_떄_화면을_가로로_회전해도_카운트가_유지된다`() {
        val activityScenario = activityRule.scenario
        plusBtn.perform(click())

        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.tv_count)).check(matches(withText("2")))
    }

    @Test
    fun `카운트가_2일_떄_화면을_가로로_회전한_후에_다시_카운트를_올리면_3이_된다`() {
        // given
        val activityScenario = activityRule.scenario
        plusBtn.perform(click())

        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // when
        plusBtn.perform(click())

        onView(withId(R.id.tv_count)).check(matches(withText("3")))
    }

    @Test
    fun `날짜_스피너에서_첫_번째_날짜를_선택하면_선택된_날짜는_2024-03-01이다`() {
        dateSpinner.perform(click())
        onData(anything()).atPosition(0).perform(click()); // LocalDate.of(2024, 3, 2))

        dateSpinner.check(matches(withSpinnerText(containsString("2024-03-01"))))
    }

    @Test
    fun `날짜_스피너에서_첫_번째_날짜를_선택하면_시간_스피너에서_첫_번째_시간은_09AM_이다`() {
        dateSpinner.perform(click())
        onData(anything()).atPosition(0).perform(click()); // LocalDate.of(2024, 3, 2))

        timeSpinner.check(matches(withSpinnerText(containsString("09:00"))))
    }

    @Test
    fun `날짜_스피너에서_두_번째_날짜를_선택하면_선택된_날짜는_2024-03-02`() {
        dateSpinner.perform(click())
        onData(anything()).atPosition(1).perform(click()); // LocalDate.of(2024, 3, 2))

        dateSpinner.check(matches(withSpinnerText(containsString("2024-03-02"))))
    }

    @Test
    fun `날짜_스피너에서_두_번째_날짜를_선택하면_시간_스피너에서_첫_번째_시간은_10AM_이다`() {
        dateSpinner.perform(click())
        onData(anything()).atPosition(1).perform(click()); // LocalDate.of(2024, 3, 2))

        timeSpinner.check(matches(withSpinnerText(containsString("10:00"))))
    }

    @Test
    fun `날짜_스피너에서_두_번째_날짜를_선택하고_화면_회전을_해도_2024-03-02_날짜가_유지된다`() {
        dateSpinner.perform(click())
        onData(anything()).atPosition(1).perform(click()); // LocalDate.of(2024, 3, 2))

        dateSpinner.check(matches(withSpinnerText(containsString("2024-03-02"))))
    }

    @Test
    fun `시간_스피너에서_두_번째_날짜를_선택하고_화면_회전을_해도_11-00_시간이_유지된다`() {
        timeSpinner.perform(click())
        onData(anything()).atPosition(1).perform(click()); // LocalDate.of(2024, 3, 1))

        timeSpinner.check(matches(withSpinnerText(containsString("11:00"))))
    }

    @Test
    fun `카운트_1을_증가시키고_화면회전_시켜도_티켓_수가_2로_유지된다`() {
        // when
        plusBtn.perform(click())

        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // then
        onView(withId(R.id.tv_count))
            .check(matches(withText("2")))
    }

    private fun clickPlusButtonMultipleTimes(attempts: Int) {
        repeat(attempts) {
            plusBtn.perform(click())
        }
    }
}
