package woowacourse.movie.presentation.booking

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.presentation.seats.SeatsActivity
import java.time.LocalTime

@Suppress("ktlint:standard:function-naming")
class BookingActivityTest {
    private lateinit var activityScenario: ActivityScenario<BookingActivity>

    @Before
    fun setUp() {
        Intents.init()

        val screening =
            Screening(
                SEOLLEUNG,
                HARRY_POTTER,
                listOf(15, 17, 19).map { LocalTime.of(it, 0) },
            )

        val intent =
            Intent(ApplicationProvider.getApplicationContext(), BookingActivity::class.java).apply {
                putExtra("screening", screening)
            }

        activityScenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun 영화_제목이_출력된다() {
        onView(withId(R.id.textview_title))
            .check(matches(withText("해리 포터와 마법사의 돌")))
    }

    @Test
    fun 상영일자가_출력된다() {
        onView(withId(R.id.textview_screeningdate))
            .check(matches(withText("상영일: 2025.05.01 ~ 2025.05.31")))
    }

    @Test
    fun 러닝타임이_출력된다() {
        onView(withId(R.id.textview_runningtime))
            .check(matches(withText("러닝타임: 152분")))
    }

    @Test
    fun 인원의_초기값은_1이다() {
        onView(withId(R.id.textview_headcount))
            .check(matches(withText("1")))
    }

    @Test
    fun 증가_버튼을_누르면_숫자가_1_증가한다() {
        onView(withId(R.id.button_increase))
            .perform(click())

        onView(withId(R.id.textview_headcount))
            .check(matches(withText("2")))
    }

    @Test
    fun 값이_2_이상일때_감소_버튼을_누르면_숫자가_1_감소한다() {
        onView(withId(R.id.button_increase))
            .perform(click())

        onView(withId(R.id.textview_headcount))
            .check(matches(withText("2")))

        onView(withId(R.id.button_decrease))
            .perform(click())

        onView(withId(R.id.textview_headcount))
            .check(matches(withText("1")))
    }

    @Test
    fun 값이_1일때_감소_버튼을_누르면_숫자가_감소하지_않는다() {
        onView(withId(R.id.button_decrease))
            .perform(click())

        onView(withId(R.id.textview_headcount))
            .check(matches(withText("1")))
    }

    @Test
    fun 화면이_회전되어도_숫자의_값은_유지된다() {
        onView(withId(R.id.button_increase))
            .perform(click())

        onView(withId(R.id.textview_headcount))
            .check(matches(withText("2")))

        activityScenario.recreate()

        onView(withId(R.id.textview_headcount))
            .check(matches(withText("2")))
    }

    @Test
    fun 예매완료_버튼을_누르면_화면이_이동되고_예매_데이터가_전달된다() {
        onView(withId(R.id.button_select)).perform(click())

        intended(hasComponent(SeatsActivity::class.java.name))

        intended(
            allOf(
                hasExtraWithKey("ticket"),
            ),
        )
    }
}
