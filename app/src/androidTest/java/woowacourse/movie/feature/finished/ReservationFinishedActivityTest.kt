package woowacourse.movie.feature.finished

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.TestFixture.firstMockTicket
import woowacourse.movie.TestFixture.saveMockTicket
import woowacourse.movie.feature.history.ReservationHistoryFragment.Companion.TICKET_ID

@RunWith(AndroidJUnit4::class)
class ReservationFinishedActivityTest {
    @get: Rule
    var activityScenarioRule: ActivityScenarioRule<ReservationFinishedActivity>

    init {
        saveMockTicket()
        activityScenarioRule = ActivityScenarioRule<ReservationFinishedActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                ReservationFinishedActivity::class.java,
            ).apply {
                putExtra(TICKET_ID, firstMockTicket.uid)
            },
        )
    }

    @Test
    fun `예매한_영화의_제목을_보여준다`() {
        onView(withId(R.id.tv_reservation_finished_movie_title)).check(matches(withText("해리 포터와 마법사의 돌")))
    }

    @Test
    fun `예매한_영화의_상영일을_보여준다`() {
        onView(
            withId(R.id.tv_reservation_finished_screening_date),
        ).check(matches(withText("2024-05-09 10:00")))
    }

    @Test
    fun `예매한_영화의_관람인원을_보여준다`() {
        onView(withId(R.id.tv_reservation_finished_head_count)).check(
            matches(
                withText(
                    "2명 |",
                ),
            ),
        )
    }

    @Test
    fun `예매한_좌석의_번호를_보여준다`() {
        onView(withId(R.id.tv_reservation_finished_seats)).check(
            matches(
                withText(
                    "A2, C3",
                ),
            ),
        )
    }

    @Test
    fun `예매한_극장의_이름을_보여준다`() {
        onView(withId(R.id.tv_reservation_finished_theater_name)).check(
            matches(
                withText(
                    "| 선릉 극장",
                ),
            ),
        )
    }

    @Test
    fun `예매한_영화의_총_결제금액을_보여준다`() {
        onView(withId(R.id.tv_reservation_finished_amount)).check(
            matches(
                withText(
                    "25,000",
                ),
            ),
        )
    }
}
