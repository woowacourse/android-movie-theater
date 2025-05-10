package woowacourse.movie

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import org.junit.After
import org.junit.Before
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
}
