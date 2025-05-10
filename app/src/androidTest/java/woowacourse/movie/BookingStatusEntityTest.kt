package woowacourse.movie

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import java.time.LocalDateTime

class BookingStatusEntityTest {
    private lateinit var bookingStatusDao: BookingStatusDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = Room.inMemoryDatabaseBuilder(context, BookingStatusDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        bookingStatusDao = database.BookingStatusDao()
    }

    @DisplayName("데이터베이스에 예약정보를 추가할 수 있다")
    @Test
    fun insertBookingStatus() {
        // given
        val bookingStatusEntity =
            BookingStatusEntity(1, "해리포터와 불의 잔", "2025-5-2", "선릉")

        // when
        bookingStatusDao.insertBookingStatusEntity(bookingStatusEntity)
        val bookingStatuses = bookingStatusDao.getAll()

        // then
        assertThat(bookingStatuses).contains(bookingStatusEntity)
    }

    @DisplayName("데이터 베이스의 예약정보를 삭제할 수 있다")
    @Test
    fun `deleteBookingStatus`() {
        // given
        val bookingStatusEntity = BookingStatusEntity(1, "해리포터와 불의 잔", "2025-5-2", "선릉")
        bookingStatusDao.insertBookingStatusEntity(bookingStatusEntity)

        // when
        bookingStatusDao.deleteBookingStatusEntity(bookingStatusEntity)
        val bookingStatuses = bookingStatusDao.getAll()

        // then
        assertThat(bookingStatuses).doesNotContain(bookingStatusEntity)
    }
}
