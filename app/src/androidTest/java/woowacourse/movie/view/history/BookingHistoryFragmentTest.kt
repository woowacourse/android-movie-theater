package woowacourse.movie.view.history

import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.db.TicketDao
import woowacourse.movie.data.db.UserDatabase
import woowacourse.movie.fixture.fakeContext
import woowacourse.movie.fixture.ticketEntity1
import woowacourse.movie.view.main.MainActivity

@RunWith(AndroidJUnit4::class)
class BookingHistoryFragmentTest {
    private lateinit var db: UserDatabase
    private lateinit var ticketDao: TicketDao

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            android.Manifest.permission.POST_NOTIFICATIONS,
        )

    @Before
    fun setup() {
        db =
            Room.inMemoryDatabaseBuilder(fakeContext, UserDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        ticketDao = db.ticketDao()

        ticketDao.insert(ticketEntity1)
    }

    @Test
    fun `바텀_네비게이션의_예매_내역_탭을_누르면_예매_내역이_표시된다`() {
        onView(withId(R.id.action_history)).perform(click())

        onView(withId(R.id.rv))
            .check(matches(isDisplayed()))
    }
}
