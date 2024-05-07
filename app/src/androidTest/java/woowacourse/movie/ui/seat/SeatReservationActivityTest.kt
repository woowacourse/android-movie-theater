package woowacourse.movie.ui.seat

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotSelected
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.repository.DummyReservation

@RunWith(AndroidJUnit4::class)
class SeatReservationActivityTest {
    private lateinit var scenario: ActivityScenario<SeatReservationActivity>

    @Before
    fun setup() {
        // 상영작, 예매 개수, 날짜를 미리 예약해둔다.
        DummyReservation.saveTimeReservation(
            screen = Screen.NULL,
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
    fun `좌석을_count_2_보다_적은_갯수만큼_선택한_좌석만_선택된다`() {
        // given
        val a1 = onView(withText("A 1"))
        val b2 = onView(withText("B 2"))
        val c3 = onView(withText("C 3"))

        // when
        a1.perform(click())
        b2.perform(click())

        // then
        a1.check((matches(isSelected())))
        b2.check((matches(isSelected())))
        c3.check((matches(isNotSelected())))
    }

    @Test
    fun `좌석을_count_2_보다_적게_선택하면_확인_버튼이_비활성화된다`() {
        // given
        val a1 = onView(withText("A 1"))

        // when
        a1.perform(click())

        // then
        onView(withId(R.id.btn_seat_reservation_complete)).check(matches(isNotEnabled()))
    }

    @Test
    fun `좌석을_count_2_만큼_선택하면_확인_버튼이_활성화된다`() {
        // given
        val a1 = onView(withText("A 1"))
        val b2 = onView(withText("B 2"))

        // when
        a1.perform(click())
        b2.perform(click())

        // then
        onView(withId(R.id.btn_seat_reservation_complete)).check(matches(isEnabled()))
    }

    @Test
    fun `확인버튼_선택_시_예매_확인_다이얼로그가_나타난다`() {
        // given
        val a1 = onView(withText("A 1"))
        val b2 = onView(withText("B 2"))

        // when
        a1.perform(click())
        b2.perform(click())

        onView(withId(R.id.btn_seat_reservation_complete)).perform(click())
        onView(withText("예매 확인")).check(matches(isDisplayed()))
        onView(withText("정말 예매하시겠습니까?")).check(matches(isDisplayed()))
    }
}
