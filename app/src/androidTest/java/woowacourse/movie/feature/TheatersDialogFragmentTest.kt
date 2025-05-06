package woowacourse.movie.feature

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.feature.model.MovieDateUiModel
import woowacourse.movie.feature.model.MovieTimeUiModel
import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.model.ScreeningUiModel
import woowacourse.movie.feature.theaters.view.TheatersDialogFragment
import woowacourse.movie.nthChildOf

@Suppress("ktlint:standard:function-naming")
class TheatersDialogFragmentTest {
    @Before
    fun setup() {
        launchFragmentInContainer<TheatersDialogFragment>(
            fragmentArgs =
                Bundle().apply {
                    putParcelableArrayList(
                        "SCREENINGS",
                        arrayListOf(
                            ScreeningUiModel(
                                movie =
                                    MovieUiModel(
                                        id = 0,
                                        title = "레디 플레이어 원",
                                        startDate = MovieDateUiModel(2025, 5, 1),
                                        endDate = MovieDateUiModel(2025, 5, 10),
                                        runningTime = 148,
                                    ),
                                theaterName = "혜화",
                                times = listOf(MovieTimeUiModel(10, 0)),
                            ),
                        ),
                    )
                },
        )
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
}
