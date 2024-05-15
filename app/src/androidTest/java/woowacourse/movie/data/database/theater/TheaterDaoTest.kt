package woowacourse.movie.data.database.theater

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.domain.MovieContent

@RunWith(AndroidJUnit4::class)
@SmallTest
class TheaterDaoTest {
    private lateinit var database: MovieDatabase
    private lateinit var dao: TheaterDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.theaterDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun 단일_상영관_정보를_삽입할_수_있다() {
        // when
        val id = dao.save(theater1)

        // then
        assertEquals(1L, id)
    }

    @Test
    fun 단일_영화_정보를_가져올_수_있다() {
        // when
        dao.save(theater1)

        // then
        val actualResult = dao.find(1L)
        assertEquals(
            TheaterEntity(
                id = 1L,
                name = "잠실",
                screeningTimes = listOf("11:00", "13:00")
            ), actualResult
        )
    }

    @Test
    fun 모든_영화_정보를_가져올_수_있다() {
        // when
        dao.save(theater1)
        dao.save(theater2)

        // then
        val actualResult = dao.findAll()
        assertEquals(
            listOf(
                TheaterEntity(
                    id = 1L,
                    name = "잠실",
                    screeningTimes = listOf("11:00", "13:00")
                ),
                TheaterEntity(
                    id = 2L,
                    name = "선릉",
                    screeningTimes = listOf("12:00", "14:00")
                )
            ), actualResult
        )
    }

    @Test
    fun 모든_영화_정보를_삭제할_수_있다() {
        // when
        dao.save(theater1)
        dao.save(theater2)
        dao.deleteAll()

        // then
        val actualResult = dao.findAll()
        assertEquals(emptyList<MovieContent>(), actualResult)
    }
}

private val theater1 = TheaterEntity(
    id = 1L,
    name = "잠실",
    screeningTimes = listOf("11:00", "13:00")
)

private val theater2 = TheaterEntity(
    id = 2L,
    name = "선릉",
    screeningTimes = listOf("12:00", "14:00")
)
