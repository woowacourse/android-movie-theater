package woowacourse.movie.view.complete

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Before
import org.junit.Test
import woowacourse.movie.fixture.fakeContext

class BookingCompleteActivityTest {
    private lateinit var intent: Intent

    @Before
    fun setUp() {
        intent = BookingCompleteActivity.Companion.newIntent(fakeContext, 1)
        ActivityScenario.launch<BookingCompleteActivity>(intent)
    }

    @Test
    fun `전달_받은_영화_이름_상영_시간_예매_인원_예매_가격을_출력한다`() {
        onView(ViewMatchers.withText("해리 포터와 마법사의 돌"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("2025.4.1 12:00"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("일반 2명"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("A1, A2"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("26,000원 (현장 결제)"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("CGV"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
