package woowacourse.movie.ui.history

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasProperty
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.data.database.ticket.TicketEntity
import woowacourse.movie.domain.UserTicket
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class MovieBookingHistoryFragmentTest {
    private lateinit var db: MovieDatabase
    private lateinit var dao: TicketDao
    private lateinit var ticket: TicketEntity

    @Before
    fun setUp() {
        val theme = R.style.Theme_Movie
        val fragmentArgs = Bundle()
        launchFragmentInContainer<MovieBookingHistoryFragment>(
            fragmentArgs,
            theme,
        )
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.ticketDao()
        thread {
            dao.save(
                TicketEntity(
                    id = 0,
                    title = "해리 포터와 마법사의 돌",
                    theater = "선릉",
                    screeningStartDateTime = "2024.03.28 21:00",
                    reservationCount = 1,
                    seats = "[A1]",
                    price = 10000
                )
            )
            ticket = dao.find(0L)
        }.join()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `화면이_띄워지면_예매_내역이_보인다`() {
        onView(allOf(isDisplayed(), withId(R.id.rv_booking_history)))
    }
}
