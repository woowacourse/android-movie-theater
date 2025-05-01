package woowacourse.movie

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.movie.adapter.MovieAdapter

class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun `리사이클러뷰가_화면에_보인다`() {
        onView(withId(R.id.recyclerView_layout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `리사이클러뷰_영화포스터가_존재하는지_확인한다`() {
        onView(withId(R.id.recyclerView_layout))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withId(R.id.recyclerView_layout))
            .check(
                matches(
                    atPosition(
                        0,
                        hasDescendant(
                            allOf(
                                withId(R.id.img_poster),
                                isDisplayed(),
                            ),
                        ),
                    ),
                ),
            )
    }

    @Test
    fun `리스트뷰_영화이름이_존재하는지_확인한다`() {
        onView(withId(R.id.recyclerView_layout))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withId(R.id.recyclerView_layout))
            .check(
                matches(
                    atPosition(
                        0,
                        hasDescendant(
                            allOf(
                                withId(R.id.tv_movie_title),
                                withText(HARRY_POTTER),
                            ),
                        ),
                    ),
                ),
            )
    }

    @Test
    fun `리스트뷰_영화상영일이_존재하는지_확인한다`() {
        onView(withId(R.id.recyclerView_layout))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withId(R.id.recyclerView_layout))
            .check(
                matches(
                    atPosition(
                        0,
                        hasDescendant(
                            allOf(
                                withId(R.id.tv_movie_screening_date),
                                withText("2025.4.1 ~ 2025.5.30"),
                            ),
                        ),
                    ),
                ),
            )
    }

    @Test
    fun `리스트뷰_영화상영시간이_존재하는지_확인한다`() {
        onView(withId(R.id.recyclerView_layout))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withId(R.id.recyclerView_layout))
            .check(
                matches(
                    atPosition(
                        0,
                        hasDescendant(
                            allOf(
                                withId(R.id.tv_movie_running_time),
                                withText("152분"),
                            ),
                        ),
                    ),
                ),
            )
    }

    @Test
    fun `리사이클러뷰_영화예매_클릭후_다이얼로그가_뜬다`() {
        onView(withId(R.id.recyclerView_layout))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(
            allOf(
                withId(R.id.btn_reserve),
                isDescendantOfA(nthChildOf(withId(R.id.recyclerView_layout), 0)),
            ),
        ).perform(click())

        onView(withText("선릉 극장")).check(matches(isDisplayed()))
    }

    @Test
    fun `리사이클러뷰에서_3번째마다_광고가_출력된다`() {
        val adPosition = 3

        onView(withId(R.id.recyclerView_layout))
            .perform(RecyclerViewActions.scrollToPosition<MovieAdapter.AdViewHolder>(adPosition))

        onView(
            allOf(
                withId(R.id.img_banner),
                isDescendantOfA(withId(R.id.recyclerView_layout)),
            ),
        ).check(matches(isDisplayed()))
    }

    private fun atPosition(
        position: Int,
        itemMatcher: Matcher<View>,
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                val viewHolder = (view as RecyclerView).findViewHolderForAdapterPosition(position)
                return itemMatcher.matches(viewHolder?.itemView)
            }
        }
    }

    fun nthChildOf(
        parentMatcher: Matcher<View>,
        childPosition: Int,
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Nth child of parent")
            }

            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup &&
                    parentMatcher.matches(parent) &&
                    parent.getChildAt(childPosition) == view
            }
        }
    }
}
