package woowacourse.movie.view.seat

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.domain.model.Booking
import woowacourse.movie.domain.model.PeopleCount
import woowacourse.movie.ext.isDisplayed
import woowacourse.movie.ext.performClick
import woowacourse.movie.fixture.fakeContext
import woowacourse.movie.view.seat.SeatActivity.Companion.newIntent
import java.time.LocalDate
import java.time.LocalTime

class SeatActivityTest {
    private lateinit var intent: Intent

    @Before
    fun setUp() {
        intent =
            newIntent(
                fakeContext,
                Booking(
                    movieTitle = "해리 포터와 마법사의 돌",
                    bookingDate = LocalDate.parse("2025-04-01"),
                    bookingTime = LocalTime.parse("12:00"),
                    count = PeopleCount(2),
                    theaterName = "선릉 극장",
                ),
            )
        ActivityScenario.launch<SeatActivity>(intent)
    }

    @Test
    fun `전달받은_영화_이름을_출력한다`() {
        onView(withText("해리 포터와 마법사의 돌")).isDisplayed()
    }

    @Test
    fun `좌석을_추가하면_좌석_가격을_출력한다`() {
        // given
        onView(withId(R.id.tv_price)).check(matches(withText("0원")))

        // when
        onView(withId(R.id.a1)).performClick()
        onView(withId(R.id.a2)).performClick()

        // then
        onView(withId(R.id.tv_price)).check(matches(withText("20,000원")))
    }

    @Test
    fun `좌석을_선택_해제하면_감소된_좌석_가격을_출력한다`() {
        // given
        onView(withId(R.id.tv_price)).check(matches(withText("0원")))

        // when
        onView(withId(R.id.a1)).performClick()
        onView(withId(R.id.tv_price)).check(matches(withText("10,000원")))

        onView(withId(R.id.a1)).performClick()

        // then
        onView(withId(R.id.tv_price)).check(matches(withText("0원")))
    }
}
