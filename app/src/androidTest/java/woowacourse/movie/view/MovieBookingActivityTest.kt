package woowacourse.movie.view

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test
import woowacourse.movie.MovieFixture
import woowacourse.movie.R
import woowacourse.movie.checkIsDisplayed
import woowacourse.movie.checkWithText
import woowacourse.movie.moviebooking.MovieBookingActivity

class MovieBookingActivityTest {
    @Before
    fun setUp() {
        val movie = MovieFixture.MOVIE
        val theater = MovieFixture.THEATER

        val intent = MovieBookingActivity.movieBookingIntent(ApplicationProvider.getApplicationContext(), movie, theater)

        ActivityScenario.launch<MovieBookingActivity>(intent)
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
    fun 예매할_영화_상영_기간이_보인다() {
        onView(withId(R.id.booking_movie_date)).checkIsDisplayed()
    }

    @Test
    fun 예매할_영화_러닝_타임이_보인다() {
        onView(withId(R.id.booking_movie_running_time)).checkIsDisplayed()
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

//    @Test
//    fun 예매할_날짜가_보인다() {
//        onView(withId(R.id.booking_date_picker)).checkWithText("2025.09.29")
//    }
//
//    @Test
//    fun 예매할_시간이_보인다() {
//        onView(withId(R.id.booking_time_picker)).checkWithText("10:00")
//    }
}
