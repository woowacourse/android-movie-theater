package woowacourse.movie

import android.content.Context
import android.content.Intent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.view.movie.MoviesActivity
import woowacourse.movie.view.movie.MoviesFragment
import woowacourse.movie.view.reservation.seat.SeatSelectActivity

class MoviesFragmentTest {
    private lateinit var scenario: ActivityScenario<SeatSelectActivity>
    private val fakeContext: Context = ApplicationProvider.getApplicationContext()

    @BeforeEach
    fun setUp() {
        scenario =
            ActivityScenario.launch(Intent(fakeContext, MoviesActivity::class.java))
    }

    @Test
    fun `영화목록이_화면에_보여야_한다`() {
        launchFragmentInContainer { MoviesFragment() }

        onView(withId(R.id.rv_movies))
            .check(matches(isDisplayed()))
    }
}
