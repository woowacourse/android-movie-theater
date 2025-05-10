package woowacourse.movie

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.data.Reservation
import woowacourse.movie.data.ReservationDao
import woowacourse.movie.data.ReservationDatabase
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_A2
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.main.MainActivity
import woowacourse.movie.mapper.toEntity
import woowacourse.movie.mapper.toUiModel

class ReservationListFragmentTest {
    private lateinit var reservationDao: ReservationDao
    private lateinit var reservationDatabase: ReservationDatabase
    private lateinit var reservation: Reservation

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            android.Manifest.permission.POST_NOTIFICATIONS,
        )

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext<Context>()
        reservationDatabase =
            Room.inMemoryDatabaseBuilder(context, ReservationDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        reservationDao = reservationDatabase.reservationDao()

        val ticket =
            createTicket(
                name = SEOLLEUNG,
                seats = listOf(SEAT_A1, SEAT_A2),
            )

        reservation = ticket.toUiModel().toEntity()

        reservationDao.insertReservation(reservation)
    }

    @Test
    fun `예매_내역_메뉴를_누르면_예매_내역이_표시된다`() {
        onView(withId(R.id.menu_reserve_list))
            .perform(click())

        onView(withId(R.id.recyclerView_reservation_list))
            .check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        reservationDatabase.close()
    }
}
