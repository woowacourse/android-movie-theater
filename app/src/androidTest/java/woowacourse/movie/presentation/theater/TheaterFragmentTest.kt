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
import org.junit.jupiter.api.Test
import woowacourse.movie.R
import woowacourse.movie.fixture.HARRY_POTTER

@Suppress("ktlint:standard:function-naming")
class TheaterFragmentTest {
    private val args =
        Bundle().apply {
            putSerializable("movie", HARRY_POTTER)
        }

    @Test
    fun 극장_목록이_출력된다() {
        launchFragmentInContainer<TheaterFragment>(args)

        onView(withId(R.id.recyclerview_theaters))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withText("선릉 극장"))
            .check(matches(isDisplayed()))
    }
}
