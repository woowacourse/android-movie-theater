package woowacourse.movie.feature.home.movie

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.feature.MovieMainActivity
import woowacourse.movie.feature.home.movie.adapter.AdvertisementViewHolder
import woowacourse.movie.feature.home.movie.adapter.MovieViewHolder

@RunWith(AndroidJUnit4::class)
class MovieHomeFragmentTest {
    @Before
    fun setUp() {
        launchFragmentInContainer<MovieHomeFragment>()
    }

    @Test
    fun `영화_목록이_화면에_표시된다`() {
        onView(withId(R.id.movieRecyclerView))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `영화_목록의_3번째_아이템은_영화가_보여진다`() {
        onView(withId(R.id.movieRecyclerView)).check(
            matches(
                matchViewHolderAtPosition(2, MovieViewHolder::class.java),
            ),
        )
    }

    @Test
    fun `영화_목록의_4번째_아이템은_광고가_보여진다`() {
        onView(withId(R.id.movieRecyclerView)).check(
            matches(
                matchViewHolderAtPosition(3, AdvertisementViewHolder::class.java),
            ),
        )
    }

    private fun matchViewHolderAtPosition(
        position: Int,
        viewHolderClass: Class<out RecyclerView.ViewHolder>,
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {}

            override fun matchesSafely(view: View?): Boolean {
                if (view !is RecyclerView) return false
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                return viewHolder != null &&
                    viewHolderClass.isInstance(
                        viewHolder,
                    )
            }
        }
    }
}
