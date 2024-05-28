package woowacourse.movie.ui.seat


import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.ReservationTicketDatabase
import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.data.repository.OfflineReservationRepository
import woowacourse.movie.domain.model.DateTime

@RunWith(AndroidJUnit4::class)
class SeatReservationActivityTest {
    private lateinit var scenario: ActivityScenario<SeatReservationActivity>

    @Before
    fun setup() {
        // 상영작, 예매 개수, 날짜를 미리 예약해둔다.
        OfflineReservationRepository(reservationTicketDao = dao).savedTimeReservationId(
            screenData = ScreenData.NULL,
            count = 2,
            dateTime = DateTime.NULL,
        )

        scenario =
            ActivityScenario.launch(
                Intent(ApplicationProvider.getApplicationContext(), SeatReservationActivity::class.java).apply {
                    putExtra("timeReservationId", 1)
                    putExtra("theaterId", 1)
                },
            )
    }

    @Test
    fun the_View_Is_Selected_When_Seats_Are_Selected() {
        // given
        val a1 = onView(withText("A 1"))
        val b2 = onView(withText("B 2"))
        val c3 = onView(withText("C 3"))

        // when
        a1.perform(click())
        b2.perform(click())

        // then
        a1.check(matches(hasColor(R.color.seat_selected)))
        b2.check(matches(hasColor(R.color.seat_selected)))
        c3.check(matches(hasColor(R.color.white)))
    }

    companion object {
        private val db = ReservationTicketDatabase.getDatabase(ApplicationProvider.getApplicationContext())
        private val dao = db.reservationDao()
    }
}

private fun hasColor(@ColorRes colorResId: Int): Matcher<in View>? {
    return object : BoundedMatcher<View, View>(View::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has background color: $colorResId")
        }

        override fun matchesSafely(view: View): Boolean {
            val backgroundColor = (view.background as? ColorDrawable)?.color
            val expectedColor = ContextCompat.getColor(view.context, colorResId)
            return backgroundColor == expectedColor
        }
    }
}

