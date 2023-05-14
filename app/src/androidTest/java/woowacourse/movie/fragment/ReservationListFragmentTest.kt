package woowacourse.movie.fragment

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.createMovieTicketModel
import woowacourse.movie.data.entity.Reservations
import woowacourse.movie.ui.activity.MainActivity
import woowacourse.movie.ui.activity.MovieTicketActivity

class ReservationListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Reservations.addItem(createMovieTicketModel())

        onView(withId(R.id.bottom_item_list))
            .perform(click())
    }

    @Test
    fun 예매_내역을_보여준다() {
        onView(withText("글로의 50가지 그림자"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 예매_내역을_누르면_예매_정보를_보여준다() {
        onView(withId(R.id.rv_reservation))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        intended(hasComponent(MovieTicketActivity::class.java.name))
    }
}
