package woowacourse.movie.feature.finished

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.TestFixture.FIRST_MOVIE_ITEM_POSITION
import woowacourse.movie.TestFixture.makeMockTicket
import woowacourse.movie.TestFixture.movies

class ReservationFinishedActivityTest {
    @get:Rule
    var activityRule =
        ActivityScenarioRule<ReservationFinishedActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                ReservationFinishedActivity::class.java,
            ).apply {
                putExtra("ticket", makeMockTicket())
            },
        )

    @Test
    fun `예매한_영화의_제목을_보여준다`() {
        onView(withId(R.id.tv_reservation_finished_movie_title)).check(matches(withText(movies[FIRST_MOVIE_ITEM_POSITION].title)))
    }

    @Test
    fun `예매한_영화의_상영일을_보여준다`() {
        onView(
            withId(R.id.tv_reservation_finished_screening_date),
        ).check(matches(withText(makeMockTicket().screeningDateTime.date)))
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
