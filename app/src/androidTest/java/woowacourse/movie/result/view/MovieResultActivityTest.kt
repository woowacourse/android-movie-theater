package woowacourse.movie.result.view

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.util.MovieIntent.MOVIE_DATE
import woowacourse.movie.util.MovieIntent.MOVIE_ID
import woowacourse.movie.util.MovieIntent.MOVIE_SEATS
import woowacourse.movie.util.MovieIntent.MOVIE_TIME
import woowacourse.movie.util.MovieIntent.RESERVATION_COUNT
import woowacourse.movie.util.MovieIntent.SELECTED_THEATER_POSITION

@RunWith(AndroidJUnit4::class)
class MovieResultActivityTest {
    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            MovieResultActivity::class.java,
        ).apply {
            putExtra(MOVIE_ID.key, 0L)
            putExtra(MOVIE_DATE.key, "2024-04-01")
            putExtra(MOVIE_TIME.key, "12:00")
            putExtra(RESERVATION_COUNT.key, 3)
            putExtra(MOVIE_SEATS.key, "A3, C2, E1")
            putExtra(SELECTED_THEATER_POSITION.key, 1)
        }

    @get:Rule
    val activityRule = ActivityScenarioRule<MovieResultActivity>(intent)

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
