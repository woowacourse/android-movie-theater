package woowacourse.movie.view.activities.seatselection

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher
import woowacourse.movie.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.view.activities.seatselection.SeatSelectionActivity.Companion.AUDIENCE_COUNT
import woowacourse.movie.view.activities.seatselection.SeatSelectionActivity.Companion.SCREENING_DATE_TIME
import woowacourse.movie.view.activities.seatselection.SeatSelectionActivity.Companion.SCREENING_ID
import woowacourse.movie.view.activities.seatselection.SeatSelectionActivity.Companion.THEATER_ID
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class SeatSelectionActivityTest {

    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        SeatSelectionActivity::class.java
    ).apply {
        putExtra(SCREENING_ID, 1L)
        putExtra(SCREENING_DATE_TIME, LocalDateTime.of(2024, 3, 1, 10, 0))
        putExtra(AUDIENCE_COUNT, 1)
        putExtra(THEATER_ID, 1L)
    }

    @get:Rule
    val rule = ActivityScenarioRule<SeatSelectionActivity>(intent)

    @Test
    fun 좌석_선택_액티비티가_실행되면_확인이라는_텍스트를_가진_버튼이_표시된다() {
        onView(withText("확인")).check(matches(isDisplayed()))
    }

    @Test
    fun 좌석들이_5행_4열의_테이블_레이아웃으로_구성되어_있다() {
        onView(withId(R.id.seat_table)).check(matches(hasChildCount(5)))
        (0..4).forEach {
            onView(
                allOf(
                    withParent(withId(R.id.seat_table)),
                    withClassName(`is`("android.widget.TableRow")),
                    withParentIndex(it)
                )
            ).check(matches(hasChildCount(4)))
        }
    }

    @Test
    fun 모든_좌석_버튼의_문자열은_행은_알파벳으로_열은_숫자로_변환된_문자열이다() {
        (0..4).forEach { row ->
            (0..3).forEach { column ->
                onView(onTable(R.id.seat_table, row, column))
                    .check(matches(withText(('A' + row).toString() + (column + 1))))
            }
        }
    }

    private fun onTable(tableId: Int, row: Int, column: Int): Matcher<View> {
        fun withParentThatTableRow(index: Int): Matcher<View> = withParent(
                allOf(
                    withParent(withId(tableId)),
                    withClassName(`is`("android.widget.TableRow")),
                    withParentIndex(index)
                )
            )

        return allOf(withParentThatTableRow(row), withParentIndex(column))
    }

    @Test
    fun 좌석을_선택하면_배경색이_노란색으로_바뀌고_하단에_할인정책과_좌석_등급을_고려한_최종_가격이_표시된다() {
        onView(onTable(R.id.seat_table, 0, 0))
            .perform(click())

        val selectedSeatColor = Color.parseColor("#FAFF00")
        onView(onTable(R.id.seat_table, 0, 0))
            .check(matches(withBackgroundColor(selectedSeatColor)))
        onView(withId(R.id.reservation_fee_tv))
            .check(matches(withText("8000원")))
    }

    private fun withBackgroundColor(expectedColor: Int): Matcher<Any> {
        return withBackgroundColor(equalTo(expectedColor))
    }

    private fun withBackgroundColor(expectedBackgroundColor: Matcher<Int>): Matcher<Any> {
        var backgroundColor: Int? = null

        return object : BoundedMatcher<Any, View>(View::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("Color did not match $backgroundColor")
            }

            override fun matchesSafely(view: View): Boolean {
                backgroundColor = (view.background as ColorDrawable).color
                return expectedBackgroundColor.matches(backgroundColor)
            }
        }
    }

    @Test
    fun 선택된_좌석을_재선택하면_선택이_해제된다() {
        onView(onTable(R.id.seat_table, 0, 0))
            .perform(click())

        onView(onTable(R.id.seat_table, 0, 0))
            .perform(click())

        val unselectedSeatColor = Color.parseColor("#FFFFFFFF")
        onView(onTable(R.id.seat_table, 0, 0))
            .check(matches(withBackgroundColor(unselectedSeatColor)))
    }

    @Test
    fun 첫_번째_행과_두_번째_행의_좌석_이름의_색깔은_보라색이다() {
        onView(onTable(R.id.seat_table, 0, 0))
            .check(matches(hasTextColor(R.color.b_class_color)))
    }

    @Test
    fun 세_번째_행과_네_번째_행의_좌석_이름의_색깔은_초록색이다() {
        onView(onTable(R.id.seat_table, 2, 0))
            .check(matches(hasTextColor(R.color.s_class_color)))
    }

    @Test
    fun 다섯_번째_행의_좌석_이름의_색깔은_파란색이다() {
        onView(onTable(R.id.seat_table, 4, 0))
            .check(matches(hasTextColor(R.color.a_class_color)))
    }

    @Test
    fun 선택한_예매_인원_수와_선택한_좌석_수가_다르면_확인_버튼은_비활성화_상태이다() {
        onView(onTable(R.id.seat_table, 0, 0))
            .perform(click())
        onView(onTable(R.id.seat_table, 0, 1))
            .perform(click())

        onView(withId(R.id.reservation_btn))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun 최종_예매를_확인하는_다이얼로그가_표시되면_취소_버튼을_누르지_않는_이상_취소되지_않는다() {
        onView(onTable(R.id.seat_table, 0, 0))
            .perform(click())
        onView(withId(R.id.reservation_btn))
            .perform(click())

        Espresso.pressBack()

        onView(withText(R.string.reservation_dialog_message)).inRoot(isDialog())
            .check(matches(isDisplayed()))
    }
}
