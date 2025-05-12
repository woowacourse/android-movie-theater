package woowacourse.movie.view.main.reservationlist

import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.model.reservationYOURNAME
import woowacourse.movie.view.main.MoviesActivity
import woowacourse.movie.view.reservation.complete.ReservationCompleteActivity

class ReservationListFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MoviesActivity::class.java)

    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    AppDatabase::class.java,
                ).allowMainThreadQueries()
                .build()

        val field = AppDatabase::class.java.getDeclaredField("INSTANCE")
        field.isAccessible = true
        field.set(null, database)

        database.reservationInfoDao().insertReservation(reservationYOURNAME)

        Intents.init()
    }

    @After
    fun teardown() {
        database.close()
        Intents.release()
    }

    @Test
    fun `예매항목_클릭시_상세화면으로_이동`() {
        onView(withId(R.id.fragment_list)).perform(click())
        Thread.sleep(1000)

        onView(withId(R.id.reservation_list_layout))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click(),
                ),
            )

        Intents.intended(hasComponent(ReservationCompleteActivity::class.java.name))
        onView(withId(R.id.tv_reservation_complete_title)).check(matches(withText("너의 이름은.")))
    }
}
