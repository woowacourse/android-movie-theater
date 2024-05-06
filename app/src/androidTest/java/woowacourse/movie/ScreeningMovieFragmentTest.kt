package woowacourse.movie

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Movies
import woowacourse.movie.screeningmovie.AdapterClickListener
import woowacourse.movie.screeningmovie.AdvertiseViewHolder
import woowacourse.movie.screeningmovie.MovieAdapter
import woowacourse.movie.screeningmovie.ScreenMovieUiModel
import woowacourse.movie.screeningmovie.ScreeningMovieFragment
import woowacourse.movie.screeningmovie.toScreenItems

class ScreeningMovieFragmentTest {
    private lateinit var fragmentScenario: FragmentScenario<ScreeningMovieFragment>

    @Before
    fun setUp() {
        fragmentScenario = launchFragmentInContainer<ScreeningMovieFragment>()
        fragmentScenario.onFragment { fragment ->
            val recyclerView =
                fragment.requireView().findViewById<RecyclerView>(R.id.rcv_screening_movie)

            val items =
                Movies(
                    listOf(
                        Movie.STUB,
                        Movie.STUB,
                        Movie.STUB,
                    ),
                ).insertAdvertisements(3).toScreenItems() + screenMovieUiModel3

            recyclerView.adapter =
                MovieAdapter(
                    object : AdapterClickListener {
                        override fun onClick(id: Long) {
                        }
                    },
                ).also { it.submitList(items) }
        }
    }

    @Test
    @DisplayName("Activity가 실행되면 뷰가 보인다.")
    fun view_is_display_when_Activity_is_created() {
        onView(withId(R.id.screening_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun `영화_리스트의_첫_번째_영화의_제목은_해리_포터와_마법사의_돌이다`() {
        onView(withId(R.id.rcv_screening_movie))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rcv_screening_movie))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    checkChildViewWithId(R.id.tv_movie_title, "해리 포터와 마법사의 돌"),
                ),
            )
    }

    @Test
    fun `영화_리스트의_다섯_번째_영화의_제목은_해리포터와_아즈카반의_죄수이다`() {
        onView(withId(R.id.rcv_screening_movie))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rcv_screening_movie))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    4,
                    checkChildViewWithId(R.id.tv_movie_title, "해리 포터와 아즈카반의 죄수"),
                ),
            )
    }

    @Test
    @DisplayName("리스트의 길이가 3 이상이면 광고가 나타난다.")
    fun advertisement_display_When_list_size_is_more_3() {
        onView(withId(R.id.rcv_screening_movie)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(AdvertiseViewHolder::class.java),
            ).atPosition(0),
        )
        onView(withDrawable(R.drawable.img_advertisement)).check(matches(isDisplayed()))
    }

    private fun withDrawable(
        @DrawableRes id: Int,
    ) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("ImageView with drawable same as drawable with id $id")
        }

        override fun matchesSafely(view: View): Boolean {
            val context = view.context
            val expectedBitmap = context.getDrawable(id)?.toBitmap()
            return view is ImageView && view.drawable.toBitmap().sameAs(expectedBitmap)
        }
    }

    private fun checkChildViewWithId(
        id: Int,
        expectedText: String,
    ) = object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return allOf(isDisplayed(), isAssignableFrom(View::class.java))
        }

        override fun getDescription(): String {
            return "Check on a child view with specified id."
        }

        override fun perform(
            uiController: UiController,
            view: View,
        ) {
            val textView = view.findViewById<TextView>(id)
            assertThat(textView.text).isEqualTo(expectedText)
        }
    }

    companion object {
        private val screenMovieUiModel3 =
            ScreenMovieUiModel(
                2,
                title = "해리 포터와 아즈카반의 죄수",
                R.drawable.img_movie_poster,
                "2024.3.1",
                "2024.3.31",
                181,
            )
    }
}
