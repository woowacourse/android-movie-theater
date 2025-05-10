package woowacourse.movie

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.movie.dao.bookingStatus.BookingStatusDao
import woowacourse.movie.dao.bookingStatus.BookingStatusDatabase
import woowacourse.movie.dao.bookingStatus.BookingStatusEntity
import woowacourse.movie.dao.bookingseats.BookingSeatDao
import woowacourse.movie.dao.bookingseats.BookingSeatDatabase
import woowacourse.movie.dao.bookingseats.BookingSeatEntity
import java.time.LocalDateTime

class BookingSeatEntityTest {
    private lateinit var bookingSeatDao: BookingSeatDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = Room.inMemoryDatabaseBuilder(context, BookingSeatDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        bookingSeatDao = database.BookingSeatDao()
    }

    @DisplayName("데이터베이스에 예약 자리를 추가할 수 있다")
    @Test
    fun insertBookingSeat() {
        // given
        val bookingSeatEntity =
            BookingSeatEntity(1, 1, 1, 1)

        // when
        bookingSeatDao.insert(listOf(bookingSeatEntity))
        val bookingSeats = bookingSeatDao.getAll()

        // then
        assertThat(bookingSeats).contains(bookingSeatEntity)
    }

    @DisplayName("데이터 베이스의 예약 자리를 삭제할 수 있다")
    @Test
    fun deleteBookingSeat() {
        // given
        val bookingSeatEntity =
            BookingSeatEntity(1, 1, 1, 1)
        bookingSeatDao.insert(listOf(bookingSeatEntity))

        // when
        bookingSeatDao.delete(bookingSeatEntity)
        val bookingSeats = bookingSeatDao.getAll()

        // then
        assertThat(bookingSeats).doesNotContain(bookingSeatEntity)
    }
}
