package woowacourse.movie

import android.content.Context
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.db.ReservationDao
import woowacourse.movie.db.ReservationDatabase
import woowacourse.movie.db.ReservationEntity
import woowacourse.movie.model.Seat
import woowacourse.movie.presentation.home.HomeActivity
import woowacourse.movie.presentation.homefragments.reservation.ReservationFragment

@RunWith(AndroidJUnit4::class)
class ReservationFragmentTest {
    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)
    private val data =
        ReservationEntity("영화 제목", "2024.05.09", "10:00", listOf(Seat(0, 0), Seat(0, 1)), "선릉")
    private lateinit var scenario: FragmentScenario<ReservationFragment>
    private lateinit var dao: ReservationDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dao = ReservationDatabase.getDatabase(context).reservationDao()

        val thread =
            Thread {
                dao.saveReservation(data)
            }
        thread.start()
        thread.join()

        scenario = launchFragmentInContainer<ReservationFragment>()
    }

    @After
    fun tearDown() {
        val thread = Thread { dao.deleteAll() }
        thread.start()
        thread.join()
    }

    @Test
    fun `예매_내역_목록이_화면에_표시된다`() {
        onView(withId(R.id.rv_reservations))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `예매한_영화의_제목이_화면에_표시된다`() {
        onView(withId(R.id.tv_reservation_title))
            .check(matches(withText("영화 제목")))
    }

    @Test
    fun `예매한_영화의_상영일자가_화면에_표시된다`() {
        onView(withId(R.id.tv_reservation_date))
            .check(matches(withText("2024.05.09")))
    }

    @Test
    fun `예매한_영화의_극장이_화면에_표시된다`() {
        onView(withId(R.id.tv_reservation_theater))
            .check(matches(withText("선릉 극장")))
    }
}
