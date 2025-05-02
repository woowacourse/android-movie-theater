package woowacourse.movie

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.booking.complete.BookingCompleteActivity
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_C1
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toUiModel

class SchedulerCompleteActivityTest {
    private lateinit var scenario: ActivityScenario<BookingCompleteActivity>

    @Before
    fun setUp() {
        val intent =
            Intent(
                ApplicationProvider.getApplicationContext(),
                BookingCompleteActivity::class.java,
            ).apply {
                putExtra("bookingResult", createTicket(SEOLLEUNG, listOf(SEAT_A1, SEAT_C1)).toUiModel())
            }

        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun `화면에_영화_제목이_표시된다`() {
        onView(withId(R.id.tv_complete_title)).check(
            matches(
                allOf(
                    withText(HARRY_POTTER),
                    isDisplayed(),
                ),
            ),
        )
    }

    @Test
    fun `화면에_선택한_영화_상영일이_표시된다`() {
        onView(withId(R.id.tv_complete_screening_date_time)).check(
            matches(
                allOf(
                    withText("2028.10.13 11:00"),
                    isDisplayed(),
                ),
            ),
        )
    }

    @Test
    fun `화면에_선택한_영화_예매인원이_표시된다`() {
        onView(withId(R.id.tv_ticket)).check(
            matches(
                allOf(
                    withText("일반 2명 | A1, C1 | 선릉 극장"),
                    isDisplayed(),
                ),
            ),
        )
    }

    @Test
    fun `화면에_선택한_영화_결제금액이_표시된다`() {
        onView(withId(R.id.tv_booking_amount)).check(
            matches(
                allOf(
                    withText("25,000원 (현장 결제)"),
                    isDisplayed(),
                ),
            ),
        )
    }
}
