package woowacourse.movie.feature.result

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
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_COUNT
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_DATE
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_SEATS
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_TIME
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_THEATER_NAME

@RunWith(AndroidJUnit4::class)
class MovieResultActivityTest {
    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            MovieResultActivity::class.java,
        ).apply {
            putExtra(KEY_MOVIE_ID, 0L)
            putExtra(KEY_MOVIE_DATE, "2024-04-01")
            putExtra(KEY_MOVIE_TIME, "12:00")
            putExtra(KEY_MOVIE_COUNT, 3)
            putExtra(KEY_MOVIE_SEATS, "A3, C2, E1")
            putExtra(KEY_SELECTED_THEATER_NAME, "선릉")
        }

    @get:Rule
    val activityRule = ActivityScenarioRule<MovieResultActivity>(intent)

    @Test
    fun `예매한_영화의_제목이_표시된다`() {
        onView(withId(R.id.tv_movie_title))
            .check(matches(withText("타이타닉 0")))
    }

    @Test
    fun `예매한_영화의_상영일과_상영_시간이_표시된다`() {
        onView(withId(R.id.tv_screening_date_time))
            .check(matches(withText("2024-04-01 12:00")))
    }

    @Test
    fun `예매한_영화의_좌석이_표시된다`() {
        onView(withId(R.id.tv_reservation_info))
            .check(matches(withText("일반 3명 | A3, C2, E1 | 선릉 극장")))
    }

    @Test
    fun `예매한_영화의_가격이_표시된다`() {
        onView(withId(R.id.tv_reservation_price))
            .check(matches(withText("37,000원 (현장 결제)")))
    }
}
