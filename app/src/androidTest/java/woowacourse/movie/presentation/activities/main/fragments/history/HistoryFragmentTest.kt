package woowacourse.movie.presentation.activities.main.fragments.history

import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.custom.RecyclerViewAssertion
import woowacourse.movie.presentation.activities.main.MainActivity
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Seat
import woowacourse.movie.presentation.model.SeatColumn
import woowacourse.movie.presentation.model.SeatRow
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketPrice
import woowacourse.movie.presentation.model.item.Reservation

@RunWith(AndroidJUnit4::class)
internal class HistoryFragmentTest {

    @get:Rule
    internal val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        activityScenario.scenario.onActivity {
            it.supportFragmentManager.commit {
                replace(R.id.fragment_container_view, HistoryFragment.getInstance())
            }
        }
        Intents.init()
    }

    /**
     * [Reservation] is fake constructor, not real constructor
     */
    private fun Reservation(movieTitle: String): Reservation =
        Reservation(
            movieTitle,
            MovieDate(2021, 10, 10),
            MovieTime(10, 10),
            Ticket(1),
            PickedSeats(listOf(Seat(SeatRow('A'), SeatColumn(1)))),
            TicketPrice(10000),
        )

    private fun setCustomAdapter(movieSize: Int) {
        activityScenario.scenario.onActivity { activity ->
            val movieRecyclerView = activity.findViewById<RecyclerView>(R.id.history_recycler_view)
            val adapter = HistoryListAdapter()

            adapter.appendAll(List(movieSize) { Reservation("Title") })
            movieRecyclerView.adapter = adapter
        }
    }

    @Test
    fun 예매목록_리사이클러뷰에_5개의_예매표가_보인다() {
        // given
        val movieSize = 5
        setCustomAdapter(movieSize)
        val expected = 5

        // when, then
        Espresso.onView(ViewMatchers.withId(R.id.history_recycler_view))
            .check(RecyclerViewAssertion.matchItemCount(expected))
    }

//    @Test
//    fun 예매목록을_클릭하면_예매확정_페이지로_이동한다() {
//        // TODO : 테스트코드 추가 작성 필요
//        // given
//        val movieSize = 5
//        setCustomAdapter(movieSize)
//
//        // when
//        Espresso.onView(ViewMatchers.withId(R.id.history_recycler_view))
//            .perform(
//                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                    0,
//                    ViewActions.click(),
//                ),
//            )
//    }
}
