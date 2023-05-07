package woowacourse.movie.reservationList

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.entity.Reservations
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.PeopleCountModel
import woowacourse.movie.model.PriceModel
import woowacourse.movie.model.TicketTimeModel
import woowacourse.movie.model.seat.ColumnModel
import woowacourse.movie.model.seat.RankModel
import woowacourse.movie.model.seat.RowModel
import woowacourse.movie.model.seat.SeatModel
import woowacourse.movie.view.main.MainActivity
import woowacourse.movie.view.movieTicket.MovieTicketActivity
import java.time.LocalDateTime

class ReservationListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Reservations.addItem(
            MovieTicketModel(
                title = "써니",
                time = TicketTimeModel(LocalDateTime.of(2023, 4, 11, 20, 0)),
                peopleCount = PeopleCountModel(1),
                seats = setOf(SeatModel(RowModel(1, 'A'), ColumnModel(1), RankModel.A)),
                price = PriceModel(10000)
            )
        )

        onView(withId(R.id.bottom_item_list))
            .perform(click())
    }

    @Test
    fun 예매_내역을_보여준다() {
        onView(withId(R.id.rv_reservation))
            .check(matches(hasChildCount(1)))
        onView(withText("써니"))
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
