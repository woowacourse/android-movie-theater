package woowacourse.movie.result.view

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.MovieApplication.Companion.database
import woowacourse.movie.R
import woowacourse.movie.data.db.ReservationHistoryEntity
import woowacourse.movie.util.MovieIntent

@RunWith(androidx.test.runner.AndroidJUnit4::class)
class MovieResultActivityTest {
    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            MovieResultActivity::class.java,
        ).apply {
            putExtra(MovieIntent.MOVIE_TICKET_ID.key, 0L)
        }

    @get:Rule
    val activityRule = ActivityScenarioRule<MovieResultActivity>(intent)

    @Before
    fun setUp() {
        database.reservationHistoryDao()
            .saveReservationHistory(
                ReservationHistoryEntity("2024-04-01", "12:00", 3, "A3, C2, E1", 0L, 1),
            )
    }

    @After
    fun tearDown() {
        database.reservationHistoryDao().clearReservations()
    }

    @Test
    fun `예매한_영화의_제목이_표시된다`() {
        onView(withId(R.id.tv_result_title))
            .check(matches(withText("타이타닉 1")))
    }

    @Test
    fun `예매한_영화의_상영일이_표시된다`() {
        onView(withId(R.id.tv_result_date))
            .check(matches(withText("2024-04-01")))
    }

    @Test
    fun `예매한_영화의_상영시간이_표시된다`() {
        onView(withId(R.id.tv_result_time))
            .check(matches(withText("12:00")))
    }

    @Test
    fun `예매한_영화의_좌석이_표시된다`() {
        onView(withId(R.id.tv_result_information))
            .check(matches(withText("일반 3명 | A3, C2, E1 | 잠실 극장")))
    }

    @Test
    fun `예매한_영화의_가격이_표시된다`() {
        onView(withId(R.id.tv_result_price))
            .check(matches(withText("37,000")))
    }
}
