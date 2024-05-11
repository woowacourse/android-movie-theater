package woowacourse.movie.model.ui.repository

import woowacourse.movie.data.database.movie.MovieContentDao
import woowacourse.movie.data.database.movie.MovieContentEntity

val HARRY_PORTER_WIZARD =
    MovieContentEntity(
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

val HARRY_PORTER_SECRET =
    MovieContentEntity(
        imageId = "thumbnail_movie2",
        title = "해리 포터와 비밀의 방",
        openingMovieDate = "2024-04-01",
        endingMoviesDate = "2024-04-28",
        runningTime = 162,
        synopsis = "《해리 포터와 비밀의 방》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, ",
        theaterIds = listOf(3L, 4L, 5L),
    )

val HARRY_PORTER_PRISONER =
    MovieContentEntity(
        imageId = "thumbnail_movie3",
        title = "해리 포터와 아즈카반의 죄수",
        openingMovieDate = "2024-05-01",
        endingMoviesDate = "2024-05-28",
        runningTime = 141,
        synopsis = "《해리 포터와 아즈카반의 죄수》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, " +

                "판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다. ",
        theaterIds = listOf(8L),
    )

val HARRY_PORTER_FIRE_GLASS =
    MovieContentEntity(
        imageId = "thumbnail_movie4",
        title = "해리 포터와 불의 잔",
        openingMovieDate = "2024-06-01",
        endingMoviesDate = "2024-06-30",
        runningTime = 157,
        synopsis = "《해리 포터와 불의 잔》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, " +

                "판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다. ",
        theaterIds = listOf(9L, 10L),
    )

object FakeMovieContentRepository : MovieContentDao {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0
    private val movieContents = mutableMapOf<Long, MovieContentEntity>()

    init {
        save(HARRY_PORTER_WIZARD)
        save(HARRY_PORTER_SECRET)
        save(HARRY_PORTER_PRISONER)
        save(HARRY_PORTER_FIRE_GLASS)
    }

    override fun save(data: MovieContentEntity): Long {
        movieContents[id] = data.copy(id = id)
        return id++
    }

    override fun find(id: Long): MovieContentEntity {
        return movieContents[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<MovieContentEntity> {
        return movieContents.map { it.value }
    }

    override fun deleteAll() {
        movieContents.clear()
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
