package woowacourse.movie.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.toBookedTicketEntity
import java.io.IOException
import java.time.LocalDateTime

class BookedTicketDaoTest {
    private lateinit var bookedTicketDao: BookedTicketDao
    private lateinit var db: BookedTicketDatabase
    private lateinit var bookedTicket: BookedTicket

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room.inMemoryDatabaseBuilder(
                context, BookedTicketDatabase::class.java,
            ).build()
        bookedTicketDao = db.bookedTicketDao()
        bookedTicket =
            BookedTicket(
                "선릉 극장",
                "해리포터",
                MovieSchedule(
                    LocalDateTime.of(2025, 5, 9, 14, 0),
                ),
                Headcount(2),
            )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `영화_티켓_정보를_데이터베이스에_저장하면_id를_반환한다`() {
        // given
        val bookedTicketEntity = bookedTicket.toBookedTicketEntity()
        val expected = 1L

        // when
        val actual = bookedTicketDao.insert(bookedTicketEntity)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `데이터베이스에_삽입한_데이터가_정상적으로_저장된다`() {
        // given
        val expected = bookedTicket.toBookedTicketEntity()

        // when
        val id = bookedTicketDao.insert(expected)

        // then
        val actual = bookedTicketDao.findBookedTicketEntityById(id)
        assertAll(
            { assertEquals(expected.theaterName, actual.theaterName) },
            { assertEquals(expected.movieTitle, actual.movieTitle) },
            { assertEquals(expected.screeningDateTime, actual.screeningDateTime) },
            { assertEquals(expected.headcount, actual.headcount) },
        )
    }

    @Test
    fun `데이터베이스에_저장된_데이터_전부를_불러온다`() {
        // given
        val bookedTicketEntities: List<BookedTicketEntity> =
            List(13) { bookedTicket.toBookedTicketEntity() }
        val expectedSize = 13
        bookedTicketEntities.forEach { bookedTicketEntity ->
            bookedTicketDao.insert(bookedTicketEntity)
        }

        // when
        val actual = bookedTicketDao.findAll()

        // then
        assertEquals(expectedSize, actual.size)
        actual.forEachIndexed { index, actualEntity ->
            val expectedId = (index + ONE_BASED_INDEX_ADJUST_NUMBER).toLong()
            assertEquals(expectedId, actualEntity.id)
        }
    }

    companion object {
        private const val ONE_BASED_INDEX_ADJUST_NUMBER = 1
    }
}
