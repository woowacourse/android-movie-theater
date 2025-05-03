package woowacourse.movie

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.booking.detail.BookingDetailActivity
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createMovie
import woowacourse.movie.fixture.createTheater
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.seat.SeatSelectionActivity
import java.time.LocalDate
import java.time.LocalTime

class BookingDetailActivityTest {
    private lateinit var scenario: ActivityScenario<BookingDetailActivity>

    @Before
    fun setUp() {
        Intents.init()

        val movie = createMovie(HARRY_POTTER)
        val theater = createTheater(SEOLLEUNG, movie)
        val intent =
            Intent(
                ApplicationProvider.getApplicationContext(),
                BookingDetailActivity::class.java,
            ).apply {
                putExtra("MOVIE_DATA", movie.toUiModel())
                putExtra("THEATER_DATA", theater)
            }

        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
        Intents.release()
        scenario.close()
    }

    @Test
    fun `화면_회전시_인원수가_유지된다`() {
        onView(withId(R.id.btn_plus)).perform(click())
        onView(withId(R.id.tv_people_count)).check(matches(withText("1")))

        scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.tv_people_count)).check(matches(withText("1")))
    }

    @Test
    fun `영화_포스터가_화면에_보인다`() {
        onView(withId(R.id.img_booking_poster))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `영화제목이_화면에_보인다`() {
        onView(withId(R.id.tv_booking_title)).check(
            matches(
                allOf(
                    withText(HARRY_POTTER),
                    isDisplayed(),
                ),
            ),
        )
    }

    @Test
    fun `영화_상영일이_화면에_보인다`() {
        onView(withId(R.id.tv_booking_screening_date)).check(
            matches(
                allOf(
                    withText("상영일: 2028.10.11 ~ 2028.10.25"),
                    isDisplayed(),
                ),
            ),
        )
    }

    @Test
    fun `영화_상영시간이_화면에_보인다`() {
        onView(withId(R.id.tv_booking_running_time)).check(
            matches(
                allOf(
                    withText("러닝타임: 152분"),
                    isDisplayed(),
                ),
            ),
        )
    }

    @Test
    fun `영화_예매_인원이_화면에_보인다`() {
        onView(withId(R.id.tv_people_count)).check(
            matches(
                allOf(
                    withText("0"),
                    isDisplayed(),
                ),
            ),
        )
    }

    @Test
    fun `minus_버튼을_눌렀을때_인원수가_0_이하면_변하지_않는다`() {
        onView(withId(R.id.tv_people_count))
            .check(matches(withText("0")))

        onView(withId(R.id.btn_minus))
            .perform(click())

        onView(withId(R.id.tv_people_count))
            .check(matches(withText("0")))
    }

    @Test
    fun `plus_버튼을_누르면_인원수가_증가한다`() {
        onView(withId(R.id.btn_plus)).perform(click())

        onView(withId(R.id.tv_people_count)).check(
            matches(
                allOf(
                    withText("1"),
                    isDisplayed(),
                ),
            ),
        )
    }

    @Test
    fun `minus_버튼을_눌렀을때_인원수가_1_이상이면_줄어든다`() {
        onView(withId(R.id.btn_plus)).perform(click())

        onView(withId(R.id.tv_people_count))
            .check(matches(withText("1")))

        onView(withId(R.id.btn_minus))
            .perform(click())

        onView(withId(R.id.tv_people_count))
            .check(matches(withText("0")))
    }

    @Test
    fun `특정_날짜를_선택했을_때_시간이_정상적으로_표시되고_선택된다`() {
        onView(withId(R.id.spinner_screening_date)).perform(click())

        val targetDate = LocalDate.of(2028, 10, 14)
        onData(`is`(targetDate))
            .inRoot(isPlatformPopup())
            .perform(click())

        onView(withId(R.id.spinner_screening_date))
            .check(matches(withSpinnerText(containsString(targetDate.toString()))))

        onView(withId(R.id.spinner_screening_time)).perform(click())

        // 주말 로직에 따라 10:00, 12:00이 떠야함
        val targetTime = LocalTime.of(12, 0)
        onData(`is`(targetTime))
            .inRoot(isPlatformPopup())
            .perform(click())

        onView(withId(R.id.spinner_screening_time))
            .check(matches(withSpinnerText(containsString("12:00"))))
    }

    @Test
    fun `예매_완료_버튼을_누르면_다음_화면으로_데이터를_가지고_넘어간다`() {
        onView(withId(R.id.spinner_screening_date)).perform(click())

        // 2028-10-13은 평일
        val targetDate = LocalDate.of(2028, 10, 13)
        onData(`is`(targetDate))
            .inRoot(isPlatformPopup())
            .perform(click())

        onView(withId(R.id.spinner_screening_time)).perform(click())

        val targetTime = LocalTime.of(11, 0)
        onData(`is`(targetTime))
            .inRoot(isPlatformPopup())
            .perform(click())

        onView(withId(R.id.btn_plus)).perform(click())
        onView(withId(R.id.btn_selection_confirm)).perform(click())

        intended(
            allOf(
                hasComponent(SeatSelectionActivity::class.java.name),
                hasExtraWithKey("TICKET_DATA"),
            ),
        )

        onView(withId(R.id.tv_seat_movie_title))
            .check(
                matches(
                    allOf(
                        withText(HARRY_POTTER),
                        isDisplayed(),
                    ),
                ),
            )
    }
}
