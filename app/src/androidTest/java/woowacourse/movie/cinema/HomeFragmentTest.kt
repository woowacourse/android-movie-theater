package woowacourse.movie.cinema

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.domain.reservation.Advertisement
import woowacourse.movie.domain.reservation.Movie
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.ui.view.cinema.HomeFragment
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    private val harryPotterChamberOfSecrets =
        Movie(
            1,
            "해리 포터와 비밀의 방",
            162,
        )

    private val harryPotterPrisonerOfAzkaban =
        Movie(
            2,
            "해리 포터와 아즈카반의 죄수",
            141,
        )
    private val harryPotterPhilosopersStone =
        Movie(
            0,
            "해리 포터와 마법사의 돌",
            152,
        )

    val fragmentArguments =
        HomeFragment.arguments(
            listOf(
                Screening(
                    harryPotterChamberOfSecrets,
                    LocalDate.of(2025, 4, 1),
                    LocalDate.of(2025, 4, 25),
                ),
                Screening(
                    harryPotterPrisonerOfAzkaban,
                    LocalDate.of(2025, 6, 1),
                    LocalDate.of(2025, 6, 25),
                ),
                Screening(
                    harryPotterPhilosopersStone,
                    LocalDate.of(2025, 6, 1),
                    LocalDate.of(2025, 6, 25),
                ),
                Advertisement(0),
            ),
        )

    private lateinit var scenario: FragmentScenario<HomeFragment>

    @BeforeEach
    fun setUp() {
        scenario =
            launchFragmentInContainer<HomeFragment>(
                fragmentArguments,
                R.style.Theme_Movie,
            )
    }

    @Test
    fun `상영_리스트가_표시된다`() {
        onView(withId(R.id.recycler_view_home_screening_movies))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `영화_목록에_영화가_세_번_노출될_때마다_광고가_한_번_노출된다`() {
        onView(withId(R.id.recycler_view_home_screening_movies))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(4))

        onView(withId(R.id.iv_item_advertisement))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `상영_정보에는_영화_제목이_표시된다`() {
        onView(withId(R.id.recycler_view_home_screening_movies))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(2))

        onView(ViewMatchers.withText("해리 포터와 마법사의 돌"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `상영_정보에는_상영일이_표시된다`() {
        onView(withId(R.id.recycler_view_home_screening_movies))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(3))

        onView(ViewMatchers.withText("상영일: 2025.4.1 ~ 2025.4.25"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `상영_정보에는_러닝타임이_표시된다`() {
        onView(withId(R.id.recycler_view_home_screening_movies))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(1))

        onView(ViewMatchers.withText("러닝타임: 141분"))
            .check(matches(isDisplayed()))
    }
}
