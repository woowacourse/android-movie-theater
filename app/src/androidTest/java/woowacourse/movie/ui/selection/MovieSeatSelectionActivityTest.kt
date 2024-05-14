package woowacourse.movie.ui.selection

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Matchers.not
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.model.data.ReservationsImpl
import woowacourse.movie.model.movie.Reservation
import woowacourse.movie.model.movie.ReservationCount
import java.time.LocalDateTime

class MovieSeatSelectionActivityTest {
    private val reservation: Reservation = ReservationsImpl.find(0L)

    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            MovieSeatSelectionActivity::class.java,
        ).run {
            putExtra(MovieSeatSelectionKey.RESERVATION_ID, 0L)
        }

    @get:Rule
    val activityRule = ActivityScenarioRule<MovieSeatSelectionActivity>(intent)

    @Test
    fun `화면이_띄워지면_영화_제목이_보인다`() {
        onView(withId(R.id.tv_movie_title))
            .check(matches(isDisplayed()))
            .check(matches(withText(reservation.title)))
    }

    @Test
    fun `화면이_띄워질_때_초기_좌석_선택_가격의_합은_0원이다`() {
        onView(withId(R.id.tv_total_seat_amount))
            .check(matches(isDisplayed()))
            .check(matches(withText("0원")))
    }

    @Test
    fun `A1_좌석을_선택한_경우_가격은_10000원이다`() {
        onView(withText("A1"))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.tv_total_seat_amount))
            .check(matches(isDisplayed()))
            .check(matches(withText("10,000원")))
    }

    @Test
    fun `예매인원이_2명인_경우_2개의_좌석을_선택하면_확인_버튼이_활성화_된다`() {
        onView(withText("A1"))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText("A2"))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.btn_confirm))
            .check(matches(isEnabled()))
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun setUp() {
            ReservationsImpl.save(
                Reservation(
                    title = "해리포터",
                    theater = "강남",
                    screeningStartDateTime = LocalDateTime.of(2024, 3, 28, 10, 0),
                    reservationCount = ReservationCount(1),
                ),
            )
        }
    }
}
