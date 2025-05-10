package woowacourse.movie

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.booking.complete.BookingCompleteActivity
import woowacourse.movie.booking.complete.BookingCompleteActivity.Companion.createIntent
import woowacourse.movie.booking.complete.BookingType
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_C1
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.ui.model.TicketUiModel

class BookingCompleteActivityTest {
    private lateinit var scenario: ActivityScenario<BookingCompleteActivity>
    private lateinit var ticket: TicketUiModel

    @Before
    fun setUp() {
        Intents.init()

        ticket =
            createTicket(
                name = SEOLLEUNG,
                seats = listOf(SEAT_A1, SEAT_C1),
            ).toUiModel()

        val intent =
            createIntent(
                ApplicationProvider.getApplicationContext(),
                BookingType.RESERVATION,
                ticket,
            )

        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
        Intents.release()
        scenario.close()
    }

    @Test
    fun `예매한_영화_정보가_화면에_표시된다`() {
        onView(withId(R.id.tv_complete_title))
            .check(matches(withText("해리 포터와 마법사의 돌")))
    }
}
