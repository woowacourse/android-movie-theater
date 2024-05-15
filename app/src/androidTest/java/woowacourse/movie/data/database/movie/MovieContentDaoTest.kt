package woowacourse.movie.data.database.movie

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
class MovieContentDaoTest {
    private lateinit var database: MovieDatabase
    private lateinit var dao: MovieContentDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.movieContentDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun 단일_영화_정보를_삽입할_수_있다() {
        // when
        val id = dao.save(movieContent1)

        // then
        assertEquals(1L, id)
    }

    @Test
    fun 단일_영화_정보를_가져올_수_있다() {
        // when
        dao.save(movieContent1)

        // then
        val actualResult = dao.find(1L)
        assertEquals(MovieContentEntity(
            id = 1L,
            imageId = "thumbnail_movie1",
            title = "해리 포터와 마법사의 돌",
            openingMovieDate = "2024-03-01",
            endingMoviesDate = "2024-03-28",
            runningTime = 152,
            synopsis =
            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, " +

                    "판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다. ",
            theaterIds = listOf(0L, 1L, 2L),
        ), actualResult)
    }

    @Test
    fun 모든_영화_정보를_가져올_수_있다() {
        // when
        dao.save(movieContent1)
        dao.save(movieContent2)

        // then
        val actualResult = dao.findAll()
        assertEquals(
            listOf(
                MovieContentEntity(
                    id = 1L,
                    imageId = "thumbnail_movie1",
                    title = "해리 포터와 마법사의 돌",
                    openingMovieDate = "2024-03-01",
                    endingMoviesDate = "2024-03-28",
                    runningTime = 152,
                    synopsis =
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, " +

                            "판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다. ",
                    theaterIds = listOf(0L, 1L, 2L),
                ),
                MovieContentEntity(
                    id = 2L,
                    imageId = "thumbnail_movie2",
                    title = "해리 포터와 마법사의 돌2",
                    openingMovieDate = "2024-04-01",
                    endingMoviesDate = "2024-04-28",
                    runningTime = 152,
                    synopsis =
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, " +

                            "판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다. ",
                    theaterIds = listOf(3L, 4L, 5L),
                )
            ), actualResult
        )
    }

    @Test
    fun 모든_영화_정보를_삭제할_수_있다() {
        // when
        dao.save(movieContent1)
        dao.save(movieContent2)
        dao.deleteAll()

        // then
        val actualResult = dao.findAll()
        assertEquals(emptyList<MovieContent>(), actualResult)
    }
}

private val movieContent1 = MovieContentEntity(
    id = 1L,
    imageId = "thumbnail_movie1",
    title = "해리 포터와 마법사의 돌",
    openingMovieDate = "2024-03-01",
    endingMoviesDate = "2024-03-28",
    runningTime = 152,
    synopsis =
    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, " +

            "판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다. ",
    theaterIds = listOf(0L, 1L, 2L),
)

private val movieContent2 = MovieContentEntity(
    id = 2L,
    imageId = "thumbnail_movie2",
    title = "해리 포터와 마법사의 돌2",
    openingMovieDate = "2024-04-01",
    endingMoviesDate = "2024-04-28",
    runningTime = 152,
    synopsis =
    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, " +

            "판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다. ",
    theaterIds = listOf(3L, 4L, 5L),
)
