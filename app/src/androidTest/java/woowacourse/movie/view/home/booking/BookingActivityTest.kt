package woowacourse.movie.view.home.booking

import android.content.pm.ActivityInfo
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.fixture.fakeContext
import woowacourse.movie.view.home.model.ScreeningInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class BookingActivityTest {
    private lateinit var scenario: ActivityScenario<BookingActivity>

    @Before
    fun setUp() {
        val baseDate = LocalDate.now()
        val intent =
            BookingActivity.newIntent(
                fakeContext,
                ScreeningInfo(
                    0,
                    "선릉 극장",
                    listOf(
                        LocalDateTime.of(baseDate.plusDays(0), LocalTime.of(12, 0)),
                        LocalDateTime.of(baseDate.plusDays(1), LocalTime.of(12, 0)),
                        LocalDateTime.of(baseDate.plusDays(2), LocalTime.of(12, 0)),
                        LocalDateTime.of(baseDate.plusDays(3), LocalTime.of(12, 0)),
                        LocalDateTime.of(baseDate.plusDays(4), LocalTime.of(12, 0)),
                    ),
                ),
            )
        scenario = ActivityScenario.launch(intent)
    }

    @Test
    fun `전달_받은_영화_이름_상영일_상영_시간을_출력한다`() {
        val startDate = LocalDate.now().plusDays(-3).format(DateTimeFormatter.ofPattern("yyyy.M.d"))
        val endDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy.M.d"))
        onView(withText("해리 포터와 마법사의 돌")).check(matches(isDisplayed()))
        onView(withText("%s ~ %s".format(startDate, endDate))).check(matches(isDisplayed()))
        onView(withText("152분")).check(matches(isDisplayed()))
    }

    @Test
    fun `상영_날짜_스피너에_날짜_목록이_표시된다`() {
        onView(withId(R.id.sp_date)).check(matches(isDisplayed()))
    }

    @Test
    fun `예매_가능_시간_스피너에_시간_목록이_표시된다`() {
        onView(withId(R.id.sp_time)).check(matches(isDisplayed()))
    }

    @Test
    fun `인원_증가_버튼을_누르면_인원이_1_증가한다`() {
        // given
        onView(withId(R.id.tv_admission_count)).check(matches(withText("1")))

        // when
        onView(withId(R.id.btn_increase)).perform(click())

        // then
        onView(withId(R.id.tv_admission_count)).check(matches(withText("2")))
    }

    @Test
    fun `인원_감소_버튼을_누르면_인원이_1_감소한다`() {
        // given
        onView(withId(R.id.tv_admission_count)).check(matches(withText("1")))

        // when
        onView(withId(R.id.btn_decrease)).perform(click())

        // then
        onView(withId(R.id.tv_admission_count)).check(matches(withText("1")))
    }

    @Test
    fun `인원은_1명_이하로_감소하지_않는다`() {
        // when
        onView(withId(R.id.tv_admission_count))
            .check(matches(withText("1")))

        // when
        onView(withId(R.id.btn_decrease)).perform(click())

        // then
        onView(withId(R.id.tv_admission_count)).check(matches(withText("1")))
    }

    @Test
    fun `화면이_회전_되어도_인원수가_유지된다`() {
        // given
        onView(withId(R.id.btn_increase)).perform(click())

        // When
        scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // then
        onView(withId(R.id.tv_admission_count)).check(matches(withText("2")))
    }
}
