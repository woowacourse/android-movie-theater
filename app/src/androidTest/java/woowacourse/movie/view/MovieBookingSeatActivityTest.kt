package woowacourse.movie.view

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.movie.MovieFixture
import woowacourse.movie.R
import woowacourse.movie.checkIsDisplayed
import woowacourse.movie.moviebookingseat.MovieBookingSeatActivity
import woowacourse.movie.performClick

class MovieBookingSeatActivityTest {
    @Before
    fun setUp() {
        val bookingStatus = MovieFixture.BOOKING_STATUS
        val theater = MovieFixture.THEATER

        val intent = MovieBookingSeatActivity.movieBookingSeatIntent(ApplicationProvider.getApplicationContext(), bookingStatus, theater)

        ActivityScenario.launch<MovieBookingSeatActivity>(intent)
    }

    @Test
    fun 스크린이_보인다() {
        onView(withId(R.id.seat_screen_text)).checkIsDisplayed()
    }

    @Test
    fun 예매할_좌석이_보인다() {
        onView(withId(R.id.seat_table)).checkIsDisplayed()
    }

    @Test
    fun 예매할_영화_제목이_보인다() {
        onView(withId(R.id.seat_movie_title)).checkIsDisplayed()
    }

    @Test
    fun 총_예매_가격이_보인다() {
        onView(withId(R.id.seat_movie_price)).checkIsDisplayed()
    }

    @Test
    fun 확인_버튼이_보인다() {
        onView(withId(R.id.seat_confirm_button)).checkIsDisplayed()
    }

    @Test
    fun 확인_버튼을_누르면_다이얼로그가_보인다() {
        onView(withText("A1")).performClick()
        onView(withText("A2")).performClick()
        onView(withId(R.id.seat_confirm_button)).performClick()
        onView(withText(R.string.confirm_reservation_message)).checkIsDisplayed()
    }
}
