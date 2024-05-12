package woowacourse.movie.ui.complete

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.common.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.data.database.ticket.TicketEntity
import woowacourse.movie.databinding.ActivityMovieReservationCompleteBinding
import woowacourse.movie.ui.selection.MovieSeatSelectionKey
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class MovieReservationCompleteActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule<MovieReservationCompleteActivity>(
        Intent(
            ApplicationProvider.getApplicationContext(),
            MovieReservationCompleteActivity::class.java,
        ).putExtra(MovieReservationCompleteKey.TICKET_ID, 0L)
    )

    private lateinit var db: MovieDatabase
    private lateinit var dao: TicketDao

    @Before
    fun setUp() = thread {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.ticketDao()
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
    }.join()

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `화면이_띄워지면_영화_제목이_보인다`() {
        onView(withId(R.id.title_text))
            .check(matches(isDisplayed()))
            .check(matches(withText("해리 포터와 마법사의 돌")))
    }

    @Test
    fun `화면이_띄워지면_상영일시가_보인다`() {
        onView(withId(R.id.screening_date_time_text))
            .check(matches(isDisplayed()))
            .check(matches(withText("2024.3.28 21:00")))
    }

    @Test
    fun `화면이_띄워지면_인원수_좌석번호_극장명이_보인다`() {
        onView(withId(R.id.selection_result_text))
            .check(matches(isDisplayed()))
            .check(matches(withText("일반 1명 | A1 | 선릉 극장")))
    }

    @Test
    fun `화면이_띄워지면_예매_금액이_보인다`() {
        onView(withId(R.id.reservation_amount_text))
            .check(matches(isDisplayed()))
            .check(matches(withText("10,000원 (현장 결제)")))
    }
}
