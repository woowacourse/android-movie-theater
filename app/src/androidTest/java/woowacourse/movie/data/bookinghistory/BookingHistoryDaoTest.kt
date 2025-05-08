package woowacourse.movie.data.bookinghistory

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.assertThrows

class BookingHistoryDaoTest {
    private lateinit var database: BookingHistoryDatabase
    private lateinit var dao: BookingHistoryDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, BookingHistoryDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        dao = database.bookingHistoryDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun 예매_기록을_추가한다() {
        // When
        dao.insert(bookingHistory1)
        val bookingHistories = dao.getAll()

        // Then
        assertThat(bookingHistories.contains(bookingHistory1)).isTrue()
    }

    @Test
    fun `여러개의_예매_기록을_추가한다`() {
        // When
        dao.insert(bookingHistory1, bookingHistory2)
        val bookingHistories = dao.getAll()

        // Then
        assertAll(
            { assertThat(bookingHistories.contains(bookingHistory1)).isTrue() },
            { assertThat(bookingHistories.contains(bookingHistory2)).isTrue() },
        )
    }

    @Test
    fun 동일한_UID를_가진_예매_기록을_추가할_수_없다() {
        // Then
        assertThrows<SQLiteConstraintException> {
            dao.insert(bookingHistory1, bookingHistory3)
        }
    }

    @Test
    fun 예매_기록을_삭제한다() {
        // Given
        dao.insert(bookingHistory1)

        // When
        dao.delete(bookingHistory1)
        val bookingHistories = dao.getAll()

        // Then
        assertThat(bookingHistories.contains(bookingHistory1)).isFalse()
    }
}