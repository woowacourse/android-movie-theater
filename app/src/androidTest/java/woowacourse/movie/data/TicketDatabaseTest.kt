package woowacourse.movie.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.TicketEntity
import woowacourse.movie.data.ticket.toEntity
import woowacourse.movie.domain.model.booking.AdmissionCount
import woowacourse.movie.domain.model.seat.Col
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.MainActivity
import java.time.LocalDate
import java.time.LocalTime

class TicketDatabaseTest {
    private lateinit var db: TicketDatabase

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db =
            Room
                .inMemoryDatabaseBuilder(context, TicketDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @Test
    fun 예매_내역이_저장된다() {
        // given
        val ticket =
            Ticket(
                "해리 포터와 마법사의 돌",
                "CGV",
                LocalDate.of(2025, 4, 1),
                LocalTime.of(12, 0),
                AdmissionCount(2),
                setOf(Seat(Col(1), Row(1)), Seat(Col(1), Row(2))),
                26000,
            )
        db.ticketDao().insert(ticket.toEntity())

        // when
        val actual: List<TicketEntity> = db.ticketDao().getAll()
        val first = actual.first()

        // then
        assertThat(actual.size).isEqualTo(1)
        with(first) {
            assertThat(movieTitle).isEqualTo("해리 포터와 마법사의 돌")
            assertThat(theaterName).isEqualTo("CGV")
            assertThat(screeningDate).isEqualTo("2025-04-01")
            assertThat(screeningTime).isEqualTo("12:00")
            assertThat(count).isEqualTo(2)
            assertThat(seats).isEqualTo("1 1,1 2")
            assertThat(price).isEqualTo(26000)
        }
    }
}
