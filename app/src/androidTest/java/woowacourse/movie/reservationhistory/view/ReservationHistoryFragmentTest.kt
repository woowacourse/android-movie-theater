package woowacourse.movie.reservationhistory.view

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.MovieMainActivity
import woowacourse.movie.R
import woowacourse.movie.data.db.ReservationHistoryDAO
import woowacourse.movie.data.db.ReservationHistoryDatabase
import woowacourse.movie.data.db.ReservationHistoryEntity

@RunWith(AndroidJUnit4::class)
class ReservationHistoryFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MovieMainActivity::class.java)

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.POST_NOTIFICATIONS)

    private lateinit var reservationHistoryDAO: ReservationHistoryDAO
    private val reservationHistoryEntity =
        ReservationHistoryEntity("2024-05-09", "11:15", 2, "A1, B1", 0L, 1)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        Thread {
            reservationHistoryDAO =
                ReservationHistoryDatabase.getInstance(context).reservationHistoryDao()
            reservationHistoryDAO.clearReservations()
            reservationHistoryDAO.saveReservationHistory(reservationHistoryEntity)
        }.start()
        Thread.sleep(1000)

        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_main_container,
                ReservationHistoryFragment(),
            ).commit()
        }
    }

    @After
    fun tearDown() {
        Thread {
            reservationHistoryDAO.clearReservations()
        }.start()
    }

    @Test
    fun `예매_내역_목록을_보여준다`() {
        onView(withId(R.id.recycler_view_history))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `예약_목록에서_예약한_영화의_제목을_확인할_수_있다`() {
        onView(withId(R.id.tv_history_title))
            .check(matches(withText("타이타닉 1")))
    }

    @Test
    fun `예약_목록에서_예약_정보를_확인할_수_있다`() {
        onView(withId(R.id.tv_history_information))
            .check(matches(withText("2024-05-09 | 11:15 | 잠실 극장")))
    }
}
