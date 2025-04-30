package woowacourse.movie.presentation.theater

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.MovieData
import woowacourse.movie.ui.constant.IntentKeys

@RunWith(AndroidJUnit4::class)
class TheaterSelectFragmentTest {
    private val movie = MovieData.movie1

    private val args = Bundle().apply {
        putSerializable(IntentKeys.MOVIE, movie)
    }

    @Test
    fun 극장_목록이_출력된다() {
        launchFragmentInContainer<TheaterSelectFragment>(args)

        onView(withId(R.id.recyclerview_theaters))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withText("선릉 극장"))
            .check(matches(isDisplayed()))
    }
}