package woowacourse.movie.presentation.activities.movielist

import android.content.Intent
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.custom.ClickViewAction
import woowacourse.movie.presentation.activities.custom.ClickViewAction.clickViewWithId
import woowacourse.movie.presentation.activities.custom.RecyclerViewAssertion
import woowacourse.movie.presentation.activities.main.MainActivity
import woowacourse.movie.presentation.activities.main.fragments.home.HomeFragment
import woowacourse.movie.presentation.activities.main.fragments.home.MovieListAdapter
import woowacourse.movie.presentation.activities.main.fragments.home.type.MovieViewType
import woowacourse.movie.presentation.activities.main.fragments.home.viewholder.NativeAdViewHolder
import woowacourse.movie.presentation.activities.ticketing.TicketingActivity
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    private val adInterval = 3

    @get:Rule
    internal val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        activityScenario.scenario.onActivity {
            it.supportFragmentManager.commit {
                replace(R.id.fragment_container_view, HomeFragment.getInstance())
            }
        }

        Intents.init()
    }

    /**
     * [Movie] is fake constructor, not real constructor
     */
    private fun Movie(movieTitle: String, runningTime: Int): Movie =
        Movie(
            movieTitle,
            LocalDate.now(),
            LocalDate.now(),
            runningTime,
            "",
            R.drawable.img_sample_movie_thumbnail1,
        )

    private fun setCustomAdapter(movieSize: Int) {
        activityScenario.scenario.onActivity { activity ->
            val movieRecyclerView = activity.findViewById<RecyclerView>(R.id.movies_rv)
            val adapter = MovieListAdapter(adInterval, Ad.provideDummy())

            adapter.appendAll(List(movieSize) { Movie("title", 120) })
            movieRecyclerView.adapter = adapter
        }
    }

    @Test
    fun 리사이클러뷰는_영화_정보_3개당_광고_1개를_나타낸다() {
        val movieSize = 20
        val adSize = 6
        val expected = movieSize + adSize

        setCustomAdapter(movieSize)

        Espresso.onView(ViewMatchers.withId(R.id.movies_rv))
            .check(RecyclerViewAssertion.matchItemCount(expected))
    }

    @Test
    fun 세_번에_한_번씩_광고가_등장한다() {
        activityScenario.scenario.onActivity { activity ->
            val recyclerView = activity.findViewById<RecyclerView>(R.id.movies_rv)

            val expected = recyclerView.adapter?.getItemViewType(adInterval)
            val actual = MovieViewType.AD.type

            assertEquals(expected, actual)
        }
    }

    @Test
    internal fun 세_번에_한_번_외에는_영화_정보가_등장한다() {
        activityScenario.scenario.onActivity { activity ->
            val recyclerView = activity.findViewById<RecyclerView>(R.id.movies_rv)

            for (position in 0 until adInterval) {
                val expected = recyclerView.adapter?.getItemViewType(position)
                val actual = MovieViewType.MOVIE.type

                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun 영화를_클릭하면_티켓팅_화면으로_이동한다() {
        // given
        Espresso.onView(ViewMatchers.withId(R.id.movies_rv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // when
        Espresso.onView(ViewMatchers.withId(R.id.movies_rv))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ClickViewAction.clickViewWithId(R.id.book_btn),
                ),
            )

        // then
        intended(IntentMatchers.hasComponent(TicketingActivity::class.java.name))
    }

    @Test
    fun `광고를_클릭하면_웹사이트가_열린다`() {
        // given
        Espresso.onView(ViewMatchers.withId(R.id.movies_rv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // when
        Espresso.onView(ViewMatchers.withId(R.id.movies_rv))
            .perform(
                RecyclerViewActions.actionOnHolderItem(
                    `is`(instanceOf(NativeAdViewHolder::class.java)),
                    clickViewWithId(R.id.native_ad_iv),
                ).atPosition(0),
            )

        // then
        intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
    }
}
