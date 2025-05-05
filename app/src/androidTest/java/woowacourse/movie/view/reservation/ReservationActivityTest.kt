package woowacourse.movie.view.reservation

import android.content.pm.ActivityInfo
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.fixture.TestData
import woowacourse.movie.matchers.performClick

@RunWith(AndroidJUnit4::class)
@Suppress("FunctionName")
class ReservationActivityTest {
    val intent =
        ReservationActivity.newIntent(
            ApplicationProvider.getApplicationContext(),
            TestData.screening,
        )

    @get:Rule
    val activityRule = ActivityScenarioRule<ReservationActivity>(intent)

    @After
    fun clear() {
        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    @Test
    fun `영화_제목을_보여준다`() {
        onView(withId(R.id.tv_reservation_title))
            .check(matches(withText("해리 포터와 마법사의 돌")))
    }

    @Test
    fun `영화_상영기간을_보여준다`() {
        onView(withId(R.id.tv_screening_period))
            .check(matches(withText("상영일: 2025.5.1 ~ 2025.5.25")))
    }

    @Test
    fun `영화_러닝타임을_보여준다`() {
        onView(withId(R.id.tv_reservation_running_time))
            .check(matches(withText("러닝타임: 152분")))
    }

    @Test
    fun `예매_인원수의_초기값은_1이다`() {
        onView(withId(R.id.tv_reservation_count))
            .check(matches(withText("1")))
    }

    @Test
    fun `예매_인원수가_3일때_마이너스_버튼을_한_번_누르면_2가_된다`() {
        // given
        onView(withId(R.id.btn_reservation_count_plus))
            .performClick()
            .performClick()

        // when
        onView(withId(R.id.btn_reservation_count_minus))
            .performClick()

        // then
        onView(withId(R.id.tv_reservation_count))
            .check(matches(withText("2")))
    }

    @Test
    fun `회전하여도_선택한_데이터가_유지된다`() {
        onView(withId(R.id.btn_reservation_count_plus))
            .performClick()

        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        onView(withId(R.id.tv_reservation_count))
            .check(matches(withText("2")))
    }
}
