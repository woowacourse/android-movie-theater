package woowacourse.movie.presentation.result

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.fixture.BOOKED_TICKET
import woowacourse.movie.presentation.booking.BookingActivity

@Suppress("ktlint:standard:function-naming")
class BookingResultActivityTest {
    @Before
    fun setUp() {
        val ticket = BOOKED_TICKET

        val intent =
            Intent(
                ApplicationProvider.getApplicationContext(),
                BookingResultActivity::class.java,
            ).apply {
                putExtra("ticket", ticket)
            }

        ActivityScenario.launch<BookingActivity>(intent)
    }

    @Test
    fun 영화_제목이_출력된다() {
        onView(withId(R.id.textview_title))
            .check(matches(withText("해리 포터와 마법사의 돌")))
    }

    @Test
    fun 상영날짜와_시간이_출력된다() {
        onView(withId(R.id.textview_screeningdatetime))
            .check(matches(withText("2025.12.31 12:00")))
    }

    @Test
    fun 예매_인원이_출력된다() {
        onView(withId(R.id.textview_headcount))
            .check(matches(withText("일반 2명")))
    }

    @Test
    fun 총_결제금액이_출력된다() {
        onView(withId(R.id.textview_amount))
            .check(matches(withText("25,000원 (현장 결제)")))
    }
}
