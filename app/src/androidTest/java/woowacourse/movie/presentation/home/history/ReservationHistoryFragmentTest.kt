package woowacourse.movie.presentation.home.history

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.presentation.history.ReservationHistoryFragment

class ReservationHistoryFragmentTest {
    @Test
    fun `영화_예매_목록이_보여진다`() {
        launchFragmentInContainer { ReservationHistoryFragment() }

        onView(withId(R.id.rv_reservation_history))
            .check(matches(isDisplayed()))
    }
}
