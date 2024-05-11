package woowacourse.movie.view.history

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.TestFixture.clearReservations
import woowacourse.movie.TestFixture.fragmentChangeToReservationHistoryFragment
import woowacourse.movie.TestFixture.makeMockTicket
import woowacourse.movie.repository.ReservationTicketRepository
import woowacourse.movie.repository.ReservationTicketRepositoryImpl
import woowacourse.movie.view.MainActivity

@RunWith(AndroidJUnit4::class)
class ReservationHistoryFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var repository: ReservationTicketRepository

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            repository = ReservationTicketRepositoryImpl(it)
            Thread {
                val reservationTicket = makeMockTicket()
                repository.saveReservationTicket(reservationTicket)
            }.start()
            Thread.sleep(1000)
            it.fragmentChangeToReservationHistoryFragment()
        }
    }

    @After
    fun clearDb(){
        activityRule.scenario.onActivity {
            Thread {
                repository.clearReservations(it)
            }.start()
        }
    }

    @Test
    fun `예약_목록을_보여준다`() {
        onView(withId(R.id.recycler_view_history))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `사용자는_예약_목록에서_상영_날짜를_확인할_수_있다`() {
        Thread.sleep(1000)
        onView(withId(R.id.ticket_date))
            .check(matches(withText("2024.3.2")))
    }

    @Test
    fun `사용자는_예약_목록에서_상영_시간을_확인할_수_있다`() {
        Thread.sleep(1000)
        onView(withId(R.id.ticket_time))
            .check(matches(withText("16:00")))
    }

    @Test
    fun `사용자는_예약_목록에서_영화_제목을_확인할_수_있다`() {
        Thread.sleep(1000)
        onView(withId(R.id.movie_title))
            .check(matches(withText("해리 포터와 마법사의 돌")))
    }

    @Test
    fun `사용자는_예약_목록에서_극장_이름을_확인할_수_있다`() {
        Thread.sleep(1000)
        onView(withId(R.id.theater_name))
            .check(matches(withText("선릉 극장")))
    }
}
