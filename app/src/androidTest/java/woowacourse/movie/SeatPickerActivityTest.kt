package woowacourse.movie

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.activity.seatpicker.SeatPickerActivity
import woowacourse.movie.movie.MovieBookingInfo

class SeatPickerActivityTest {

    private val intent =
        Intent(ApplicationProvider.getApplicationContext(), SeatPickerActivity::class.java)
            .putExtra(
                BundleKeys.MOVIE_BOOKING_INFO_KEY,
                MovieBookingInfo(
                    title = "한산",
                    date = "2023.4.11",
                    time = "10:00",
                    ticketCount = 2
                )
            )

    @get:Rule
    val activityRule = ActivityScenarioRule<SeatPickerActivity>(intent)

    @Test
    fun 넘겨져온_영화_제목을_출력한다() {
        onView(withId(R.id.tv_seat_picker_movie)).check(matches(withText("한산")))
    }

    @Test
    fun 좌석을_클릭하면_해당_좌석이_색이_바뀐다() {
        // when
        onView(withText("A1")).perform(click())

        // then
        onView(withText("A1")).check(matches(hasBackgroundColor(R.color.picked_seat_color)))
    }

    @Test
    fun 좌석을_클릭하면_해당_좌석의_가격으로_계산된_값이_변경된다() {
        // when
        onView(withText("C1")).perform(click())

        // then
        onView(withId(R.id.tv_seat_picker_ticket_price)).check(matches(withText("13,000원")))
    }

    @Test
    fun 선택된_좌석을_클릭하면_선택이_해제되며_원래의_색으로_돌아간다() {
        // given
        onView(withText("A1")).perform(click())

        // when
        onView(withText("A1")).perform(click())

        // then
        onView(withText("A1")).check(matches(hasBackgroundColor(R.color.unpicked_seat_color)))
    }

    @Test
    fun 선택된_좌석을_클릭하면_가격은_선택된_좌석의_가격만큼_뺀값으로_변경된다() {
        // given
        onView(withText("A1")).perform(click())

        // when
        onView(withText("A1")).perform(click())

        // then
        onView(withId(R.id.tv_seat_picker_ticket_price)).check(matches(withText("0원")))
    }

    @Test
    fun 티켓갯수만큼_좌석을_선택했다면_확인버튼이_활성화된다() {
        // when
        onView(withText("B1")).perform(click())
        onView(withText("B2")).perform(click())

        // then
        onView(withId(R.id.bt_seat_picker_done)).check(matches(isClickable()))
    }

    @Test
    fun 선택된_좌석을_재선택하여_티켓갯수보다_적게_선택되어있다면_확인버튼이_비활성화된다() {
        // given
        onView(withText("B2")).perform(click())
        onView(withText("B3")).perform(click())

        // when
        onView(withText("B2")).perform(click())

        // then
        onView(withId(R.id.bt_seat_picker_done)).check(matches(isNotEnabled()))
    }

    @Test
    fun 좌석을_선택하고_확인_버튼을_누를시_AleartDialog가_띄워진다() {
        // given
        onView(withText("B2")).perform(click())
        onView(withText("B3")).perform(click())

        // when
        onView(withId(R.id.bt_seat_picker_done)).perform(click())

        // then
        onView(withText("예매 확인")).check(matches(isDisplayed()))
    }

    @Test
    fun AleartDialog가_띄어져_있을_때_배경을_터치해도_AleartDialog가_사라지지_않는다() {
        // given
        onView(withText("B2")).perform(click())
        onView(withText("B3")).perform(click())
        onView(withId(R.id.bt_seat_picker_done)).perform(click())

        // when
        val x = 50
        val y = 50
        // 0, 0 좌표는 디바이스 기준 왼쪽 상단 꼭지점
        val clickAction = GeneralClickAction(
            Tap.SINGLE,
            { view ->
                val screenPos = IntArray(2)
                view.getLocationOnScreen(screenPos)
                val screenX = (screenPos[0] + x).toFloat()
                val screenY = (screenPos[1] + y).toFloat()
                floatArrayOf(screenX, screenY)
            },
            Press.FINGER,
            InputDevice.SOURCE_MOUSE,
            MotionEvent.BUTTON_PRIMARY
        )
        onView(isRoot()).perform(clickAction)

        // then
        onView(withText("예매 확인")).check(matches(isDisplayed()))
    }

    private fun hasBackgroundColor(@ColorRes color: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("has background color resource $color")
            }

            override fun matchesSafely(item: View?): Boolean {
                return item?.background is ColorDrawable &&
                    (item.background as ColorDrawable).color == ContextCompat.getColor(
                    item.context,
                    color,
                )
            }
        }
    }
}
