package woowacourse.movie.feature

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.MovieTime
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.feature.mapper.toUi
import woowacourse.movie.feature.theaters.view.TheatersDialogFragment

@Suppress("ktlint:standard:function-naming")
class TheatersDialogFragmentTest {
    val factory = createTheatersDialogFragmentFactory()

    @Before
    fun setup() {
        launchFragmentInContainer<TheatersDialogFragment>(factory = factory)
    }

    @Test
    fun 극장_이름이_출력된다() {
        onView(
            allOf(
                withId(R.id.tv_theater_name),
                isDescendantOfA(nthChildOf(withId(R.id.rv_theaters), 0)),
            ),
        ).check(matches(withText("혜화 극장")))
    }

    @Test
    fun 상영_시간_개수가_출력된다() {
        onView(
            allOf(
                withId(R.id.tv_theater_time),
                isDescendantOfA(nthChildOf(withId(R.id.rv_theaters), 0)),
            ),
        ).check(matches(withText("1개의 상영 시간")))
    }

    private fun nthChildOf(
        parentMatcher: Matcher<View>,
        childPosition: Int,
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) = Unit

            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup &&
                    parentMatcher.matches(parent) &&
                    parent.getChildAt(childPosition) == view
            }
        }
    }

    private fun createTheatersDialogFragmentFactory(): FragmentFactory =
        object : FragmentFactory() {
            override fun instantiate(
                classLoader: ClassLoader,
                className: String,
            ): Fragment =
                TheatersDialogFragment(
                    screenings =
                        listOf(
                            Screening(
                                Movie(
                                    title = "레디 플레이어 원",
                                    startDate = MovieDate(2025, 5, 1),
                                    endDate = MovieDate(2025, 5, 10),
                                    runningTime = 148,
                                ),
                                "혜화",
                                listOf(MovieTime(10, 0)),
                            ).toUi(),
                        ),
                    navigateToBookingDetail = {},
                )
        }
}
