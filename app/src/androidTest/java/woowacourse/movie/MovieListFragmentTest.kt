package woowacourse.movie

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie
import woowacourse.movie.movielist.AdapterClickListener
import woowacourse.movie.movielist.MovieAdapter
import woowacourse.movie.movielist.MovieListFragment
import woowacourse.movie.movielist.MovieViewHolder
import woowacourse.movie.movielist.uimodel.AdvertisementUiModel
import woowacourse.movie.movielist.uimodel.toAdvertisementUiModel
import woowacourse.movie.movielist.uimodel.toMovieUiModel

class MovieListFragmentTest {
    private lateinit var fragmentScenario: FragmentScenario<MovieListFragment>

    @Before
    fun setUp() {
        fragmentScenario = launchFragmentInContainer<MovieListFragment>()
        fragmentScenario.onFragment { fragment ->
            val recyclerView = fragment.requireView().findViewById<RecyclerView>(R.id.rcv_screening)

            val movies =
                listOf(
                    Movie.STUB_A,
                ).map { it.toMovieUiModel() }

            val advertisements: List<AdvertisementUiModel> =
                listOf(
                    Advertisement(),
                ).map { it.toAdvertisementUiModel() }

            val items = movies + advertisements

            recyclerView.adapter =
                MovieAdapter(
                    items,
                    object : AdapterClickListener {
                        override fun onClick(id: Long) {
                        }
                    },
                )
        }
    }

    @Test
    @DisplayName("Activity가 실행되면 뷰가 보인다.")
    fun view_is_display_when_Activity_is_created() {
        onView(withId(R.id.screening)).check(matches(isDisplayed()))
    }

    @Test
    fun `영화_리스트_안의_영화에_제목이_보여진다`() {
        onView(withId(R.id.rcv_screening))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rcv_screening))
            .perform(
                RecyclerViewActions.scrollToHolder(
                    instanceOf(MovieViewHolder::class.java),
                ).atPosition(0),
            )
        onView(withId(R.id.tv_movie_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `영화_리스트_안의_영화에_썸네일이_보여진다`() {
        onView(withId(R.id.rcv_screening))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rcv_screening))
            .perform(
                RecyclerViewActions.scrollToHolder(
                    instanceOf(MovieViewHolder::class.java),
                ).atPosition(0),
            )
        onView(withId(R.id.iv_movie_post))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `영화_리스트_안의_광고에_이미지가_정상적으로_보여진다`() {
        onView(withId(R.id.rcv_screening)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1),
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
}
