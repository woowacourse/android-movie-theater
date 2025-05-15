package woowacourse.movie.feature

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.repository.BookingRepositoryImpl
import woowacourse.movie.domain.model.BookingInfo
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.MovieSeats
import woowacourse.movie.domain.model.MovieTime
import woowacourse.movie.domain.model.TicketCount
import woowacourse.movie.domain.repository.BookingRepository
import java.time.LocalDate
import java.time.LocalTime
import kotlin.concurrent.thread

@Suppress("ktlint:standard:function-naming")
class BookingRepositoryTest {
    private lateinit var repository: BookingRepository

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database =
            Room
                .inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
                .build()

        repository = BookingRepositoryImpl(database.bookingDao())
    }

    @Test
    fun 예매_내역을_저장하고_불러올_수_있다() {
        val bookingInfo =
            BookingInfo(
                id = 0,
                movie =
                    Movie(
                        id = 0,
                        title = "레디 플레이어 원",
                        startDate = MovieDate(LocalDate.of(2025, 5, 11)),
                        endDate = MovieDate(LocalDate.of(2025, 9, 28)),
                        runningTime = 140,
                    ),
                theaterName = "강남 CGV",
                date = MovieDate(LocalDate.of(2025, 5, 11)),
                time = MovieTime(LocalTime.of(12, 0)),
                seats = MovieSeats(),
                ticketCount = TicketCount(),
            )
        thread { repository.saveBookingHistory(bookingInfo) }

        thread {
            val savedBookingHistory = repository.fetchBookingHistory()
            assertThat(savedBookingHistory.map { it.id }).contains(bookingInfo.id)
        }
    }
}
