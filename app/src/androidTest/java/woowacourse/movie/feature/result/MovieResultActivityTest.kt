package woowacourse.movie.feature.result

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.AfterClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.ticket.RoomTicketRepository
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.feature.movieId
import woowacourse.movie.feature.screeningDate
import woowacourse.movie.feature.screeningTime
import woowacourse.movie.feature.selectedSeats
import woowacourse.movie.feature.theaterName
import woowacourse.movie.util.MovieIntentConstant.KEY_TICKET_ID

@RunWith(AndroidJUnit4::class)
class MovieResultActivityTest {
    @get:Rule
    val activityRule: ActivityScenarioRule<MovieResultActivity>

    init {
        val ticketId =
            ticketRepository.save(movieId, screeningDate, screeningTime, selectedSeats, theaterName)
        val intent =
            Intent(
                ApplicationProvider.getApplicationContext(),
                MovieResultActivity::class.java,
            ).apply {
                putExtra(KEY_TICKET_ID, ticketId)
            }
        activityRule = ActivityScenarioRule<MovieResultActivity>(intent)
    }

    @Test
    fun `예매한_영화의_제목이_표시된다`() {
        onView(withId(R.id.tv_movie_title))
            .check(matches(withText("타이타닉 0")))
    }

    @Test
    fun `예매한_영화의_상영일과_상영_시간이_표시된다`() {
        onView(withId(R.id.tv_screening_date_time))
            .check(matches(withText("2024.4.1 12:30")))
    }

    @Test
    fun `예매한_영화의_좌석이_표시된다`() {
        onView(withId(R.id.tv_reservation_info))
            .check(matches(withText("일반 3명 | B2, C3, D4 | 선릉 극장")))
    }

    @Test
    fun `예매한_영화의_가격이_표시된다`() {
        onView(withId(R.id.tv_reservation_price))
            .check(matches(withText("40,000원 (현장 결제)")))
    }

    companion object {
        private val ticketRepository =
            RoomTicketRepository(
                TicketDatabase.instance(ApplicationProvider.getApplicationContext()).ticketDao(),
            )

        @JvmStatic
        @AfterClass
        fun tearDown() {
            ticketRepository.deleteAll()
        }
    }
}
