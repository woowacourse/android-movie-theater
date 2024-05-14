package woowacourse.movie.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ReservationDaoTest {
    private lateinit var reservationDao: ReservationDao
    private lateinit var db: ReservationDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room.inMemoryDatabaseBuilder(
                context,
                ReservationDatabase::class.java,
            ).build()
        reservationDao = db.reservationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun when_insert_executed_database_size_increases_one() {
        val dao = reservationDao

        // given
        val size = dao.getAll().size

        // when
        dao.insert(FAKE_RESERVATION_1)

        // then
        assertEquals(size + 1, dao.getAll().size)
    }

    @Test
    fun when_insert_all_executed_database_size_increases_as_lists_size() {
        val dao = reservationDao

        // given
        val size = dao.getAll().size

        // when
        dao.insertAll(*FAKE_RESERVATION_LIST)

        // then
        assertEquals(size + FAKE_RESERVATION_LIST.size, dao.getAll().size)
    }

    @Test
    fun default_size_of_database_is_zero() {
        val dao = reservationDao

        assertEquals(0, dao.getAll().size)
    }

    @Test
    fun id_of_first_data_is_one() {
        val dao = reservationDao

        // given
        dao.insert(FAKE_RESERVATION_1)

        // when
        val data = dao.selectWithId(1L)

        // then
        assertEquals(1L, data?.id)
    }

    @Test
    fun can_find_with_an_id() {
        val dao = reservationDao

        // given
        dao.insertAll(*FAKE_RESERVATION_LIST)

        // when
        val data = dao.selectWithId(1L)

        // then
        assertEquals(FAKE_RESERVATION_1, data)
    }

    @Test
    fun when_delete_executed_database_size_decrease_one() {
        val dao = reservationDao

        // given
        dao.insertAll(*FAKE_RESERVATION_LIST)

        // when
        dao.deleteWithId(1L)

        // then
        assertEquals(FAKE_RESERVATION_LIST.size - 1, dao.getAll().size)
    }

    fun when_delete_executed_the_data_deleted() {
        val dao = reservationDao

        // given
        dao.insertAll(*FAKE_RESERVATION_LIST)

        // when
        dao.deleteWithId(1L)

        // then
        assertEquals(false, dao.getAll().contains(FAKE_RESERVATION_1))
    }

    companion object {
        private val FAKE_RESERVATION_1 =
            ReservationData(
                theaterId = 1L,
                movieTitle = "해리 포터와 마법사의 돌",
                screenDate = "2024.5.11",
                screenTime = "17:00",
                selectedSeats = "A1,A2",
                totalPrice = "20000",
            )

        private val FAKE_RESERVATION_2 =
            ReservationData(
                theaterId = 2L,
                movieTitle = "해리 포터와 마법사의 돌",
                screenDate = "2024.5.11",
                screenTime = "17:00",
                selectedSeats = "A1,A2",
                totalPrice = "20000",
            )

        private val FAKE_RESERVATION_3 =
            ReservationData(
                theaterId = 3L,
                movieTitle = "해리 포터와 마법사의 돌",
                screenDate = "2024.5.11",
                screenTime = "17:00",
                selectedSeats = "A1,A2",
                totalPrice = "20000",
            )

        private val FAKE_RESERVATION_LIST =
            arrayOf(
                FAKE_RESERVATION_1,
                FAKE_RESERVATION_2,
                FAKE_RESERVATION_3,
            )
    }
}
