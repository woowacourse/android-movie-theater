package woowacourse.movie.cinema

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.domain.reservation.Advertisement
import woowacourse.movie.domain.reservation.Movie
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.view.cinema.ScreeningActivity
import java.time.LocalDate

class ScreeningActivityTest {
    @get:Rule
    val activityRule =
        ActivityScenarioRule<ScreeningActivity>(
            ScreeningActivity.Companion.testIntent(
                ApplicationProvider.getApplicationContext(),
                arrayOf(
                    Screening(
                        Movie(
                            0,
                            "해리 포터와 마법사의 돌",
                            152,
                        ),
                        LocalDate.of(2025, 4, 1),
                        LocalDate.of(2025, 4, 25),
                    ),
                    Screening(
                        Movie(
                            1,
                            "해리 포터와 비밀의 방",
                            162,
                        ),
                        LocalDate.of(2025, 5, 1),
                        LocalDate.of(2025, 5, 25),
                    ),
                    Screening(
                        Movie(
                            2,
                            "해리 포터와 아즈카반의 죄수",
                            141,
                        ),
                        LocalDate.of(2025, 6, 1),
                        LocalDate.of(2025, 6, 25),
                    ),
                    Advertisement(0, "우아한테크코스"),
                ),
            ),
        )

    @Test
    fun `상영_리스트가_표시된다`() {
        onView(ViewMatchers.withId(R.id.rv_screening_movies))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `영화_목록에_영화가_세_번_노출될_때마다_광고가_한_번_노출된다`() {
        onView(ViewMatchers.withId(R.id.rv_screening_movies))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(3))

        onView(ViewMatchers.withId(R.id.iv_item_advertisement))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `상영_정보에는_영화_제목이_표시된다`() {
        onView(ViewMatchers.withId(R.id.rv_screening_movies))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(2))

        onView(ViewMatchers.withText("해리 포터와 아즈카반의 죄수"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `상영_정보에는_상영일이_표시된다`() {
        onView(ViewMatchers.withId(R.id.rv_screening_movies))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(2))

        onView(ViewMatchers.withText("상영일: 2025.6.1 ~ 2025.6.25"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `상영_정보에는_러닝타임이_표시된다`() {
        onView(ViewMatchers.withId(R.id.rv_screening_movies))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(2))

        onView(ViewMatchers.withText("러닝타임: 141분"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `영화를_선택하면_극장을_선택할_수_있는_창이_나온다`() {
    }
}
