package woowacourse.movie.view

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.MovieFixture
import woowacourse.movie.R
import woowacourse.movie.checkIsDisplayed
import woowacourse.movie.checkWithSpinnerText
import woowacourse.movie.checkWithText
import woowacourse.movie.moviebooking.MovieBookingActivity

class MovieBookingActivityTest {
    private lateinit var scenario: ActivityScenario<MovieBookingActivity>

    @Before
    fun setUp() {
        val movie = MovieFixture.MOVIE
        val theater = MovieFixture.THEATER

        val intent = MovieBookingActivity.movieBookingIntent(ApplicationProvider.getApplicationContext(), movie, theater)

        scenario = ActivityScenario.launch(intent)
    }

    @Test
    fun 예매할_영화_포스터가_보인다() {
        onView(withId(R.id.booking_movie_poster)).checkIsDisplayed()
    }

    @Test
    fun 예매할_영화_제목이_보인다() {
        onView(withId(R.id.booking_movie_title)).checkIsDisplayed()
    }

    @Test
    fun 인텐트로_전달된_영화_제목이_일치한다() {
        onView(withId(R.id.booking_movie_title))
            .checkWithText("해리포터와 마법사의 돌")
    }

    @Test
    fun 예매할_영화_상영_기간이_보인다() {
        onView(withId(R.id.booking_movie_date)).checkIsDisplayed()
    }

    @Test
    fun 인텐트로_전달된_영화_상영_기간_일치한다() {
        onView(withId(R.id.booking_movie_date))
            .checkWithText(MovieFixture.HARRY_POTTER_DATE)
    }

    @Test
    fun 예매할_영화_러닝_타임이_보인다() {
        onView(withId(R.id.booking_movie_running_time)).checkIsDisplayed()
    }

    @Test
    fun 인텐트로_전달된_영화_러닝_타임이_일치한다() {
        onView(withId(R.id.booking_movie_running_time))
            .checkWithText(MovieFixture.HARRY_POTTER_RUNNING_TIME)
    }

    @Test
    fun 인원_수_증가_버튼이_보인다() {
        onView(withId(R.id.booking_plus_member_count)).checkIsDisplayed()
    }

    @Test
    fun 인원_수_감소_버튼이_보인다() {
        onView(withId(R.id.booking_minus_member_count)).checkIsDisplayed()
    }

    @Test
    fun 예매할_인원_수가_보인다() {
        onView(withId(R.id.booking_member_count)).checkWithText("1")
    }

    @Test
    fun 예매_완료_버튼이_보인다() {
        onView(withId(R.id.booking_complete_button)).checkIsDisplayed()
    }

    @Test
    fun 예매할_날짜가_보인다() {
        onView(withId(R.id.booking_date_picker)).checkWithSpinnerText("3025-04-01")
    }

    @Test
    fun 예매할_시간이_보인다() {
        onView(withId(R.id.booking_time_picker)).checkWithSpinnerText("09:00")
    }

    @After
    fun tearDown() {
        scenario.close()
    }
}
