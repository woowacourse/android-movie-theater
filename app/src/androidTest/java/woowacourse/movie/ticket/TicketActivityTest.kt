package woowacourse.movie.ticket

import android.text.Layout
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.ui.view.ticket.TicketActivity
import java.time.LocalDateTime

class TicketActivityTest {
    @get:Rule
    val activityRule: ActivityScenarioRule<TicketActivity> =
        ActivityScenarioRule(
            TicketActivity.Companion.newIntent(
                ApplicationProvider.getApplicationContext(),
                title = "해리 포터와 마법사의 돌",
                count = 2,
                showtime = LocalDateTime.of(2025, 4, 15, 12, 0),
                seats = setOf(Seat(1, 1), Seat(2, 2)),
                cinemaName = "선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장",
            ),
        )

    @Test
    fun `영화_제목이_표시된다`() {
        Espresso
            .onView(ViewMatchers.withId(R.id.tv_ticket_movie_title))
            .check(ViewAssertions.matches(ViewMatchers.withText("해리 포터와 마법사의 돌")))
    }

    @Test
    fun `상영_시각이_표시된다`() {
        Espresso
            .onView(ViewMatchers.withId(R.id.tv_ticket_showtime))
            .check(ViewAssertions.matches(ViewMatchers.withText("2025.4.15 12:00")))
    }

    @Test
    fun `인원_수가_표시된다`() {
        Espresso
            .onView(ViewMatchers.withId(R.id.tv_ticket_description))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.withText("일반 2명 | A1, B2 | 선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장,선릉 극장"),
                ),
            )
    }

    @Test
    fun `가격이_표시된다`() {
        Espresso
            .onView(ViewMatchers.withId(R.id.tv_ticket_price))
            .check(ViewAssertions.matches(ViewMatchers.withText("26,000원 (현장 결제)")))
    }

    @Test
    fun `예매_완료_화면에서_극장_이름이_길_경우_말줄임표로_표시되어야_한다`() {
        Espresso
            .onView(ViewMatchers.withId(R.id.tv_ticket_description))
            .check(ViewAssertions.matches(ellipsized()))
    }

    private fun ellipsized() =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with ellipsized text")
            }

            override fun matchesSafely(view: View): Boolean {
                val textView: TextView = view as? TextView ?: return false
                val layout: Layout = textView.layout
                val lineCount = layout.lineCount
                if (lineCount > 0) {
                    val lastLineIndex = lineCount - 1
                    val ellipsisCount = layout.getEllipsisCount(lastLineIndex)
                    if (ellipsisCount > 0) {
                        return true
                    }
                }
                return false
            }
        }
}
