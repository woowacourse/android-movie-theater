package woowacourse.movie.ui.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.database.movie.MovieContentDao
import woowacourse.movie.data.database.movie.MovieContentEntity
import woowacourse.movie.ui.home.adapter.MovieViewHolder

@RunWith(AndroidJUnit4::class)
class MovieHomeFragmentTest {
    private lateinit var db: MovieDatabase
    private lateinit var movieContent: MovieContentEntity

    @Before
    fun setUp() {
        launchFragmentInContainer<MovieHomeFragment>()
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        movieContent = db.movieContentDao().find(0L)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `화면이_띄워지면_영화_목록이_보인다`() {
        onView(withId(R.id.movie_content_list)).check(matches(isDisplayed()))
    }

    @Test
    fun `화면이_띄워지면_영화_제목이_보인다`() {
        onView(withId(R.id.movie_content_list))
            .perform(RecyclerViewActions.scrollToPosition<MovieViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText(movieContent.title), isDisplayed()))))
    }

    @Test
    fun `화면이_띄워지면_상영일이_보인다`() {
        onView(withId(R.id.movie_content_list))
            .perform(RecyclerViewActions.scrollToPosition<MovieViewHolder>(0))
            .check(
                matches(
                    hasDescendant(allOf(withText("상영일: 2024.3.1 ~ 2024.3.28"), isDisplayed())),
                ),
            )
    }

    @Test
    fun `화면이_띄워지면_러닝타임이_보인다`() {
        onView(withId(R.id.movie_content_list))
            .perform(RecyclerViewActions.scrollToPosition<MovieViewHolder>(0))
            .check(
                matches(
                    hasDescendant(
                        allOf(
                            withText("러닝타임: ${movieContent.runningTime}분"),
                            isDisplayed(),
                        ),
                    ),
                ),
            )
    }

    @Test
    fun `화면이_띄워지면_지금_예매_버튼이_보인다`() {
        onView(withId(R.id.movie_content_list))
            .perform(RecyclerViewActions.scrollToPosition<MovieViewHolder>(0))
            .check(
                matches(
                    hasDescendant(allOf(withText("지금 예매"), isDisplayed())),
                ),
            )
    }
}
