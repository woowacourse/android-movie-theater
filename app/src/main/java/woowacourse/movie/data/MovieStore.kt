package woowacourse.movie.data

import woowacourse.movie.domain.model.movies.Movie
import java.time.LocalDate

class MovieStore {
    private val movies: MutableMap<Int, Movie> = mutableMapOf()

    init {
        initData()
    }

    private fun initData() {
        val movieList =
            listOf(
                Movie(
                    id = 0,
                    title = "해리 포터와 마법사의 돌",
                    posterResource = "harry_potter_1",
                    screeningStartDate = LocalDate.of(2025, 5, 1),
                    screeningEndDate = LocalDate.of(2025, 6, 5),
                    runningTime = 152,
                ),
                Movie(
                    id = 1,
                    title = "해리 포터와 비밀의 방",
                    posterResource = "harry_potter_2",
                    screeningStartDate = LocalDate.of(2025, 5, 3),
                    screeningEndDate = LocalDate.of(2025, 6, 7),
                    runningTime = 162,
                ),
                Movie(
                    id = 2,
                    title = "해리 포터와 아즈카반의 죄수",
                    posterResource = "harry_potter_3",
                    screeningStartDate = LocalDate.of(2025, 5, 5),
                    screeningEndDate = LocalDate.of(2025, 6, 10),
                    runningTime = 141,
                ),
                Movie(
                    id = 3,
                    title = "해리 포터와 불의 잔",
                    posterResource = "harry_potter_4",
                    screeningStartDate = LocalDate.of(2025, 5, 7),
                    screeningEndDate = LocalDate.of(2025, 6, 12),
                    runningTime = 157,
                ),
                Movie(
                    id = 4,
                    title = "해리 포터와 불사조 기사단",
                    posterResource = "harry_potter_5",
                    screeningStartDate = LocalDate.of(2025, 5, 9),
                    screeningEndDate = LocalDate.of(2025, 6, 15),
                    runningTime = 157,
                ),
                Movie(
                    id = 5,
                    title = "해리 포터와 혼혈 왕자",
                    posterResource = "harry_potter_6",
                    screeningStartDate = LocalDate.of(2025, 5, 11),
                    screeningEndDate = LocalDate.of(2025, 6, 18),
                    runningTime = 157,
                ),
                Movie(
                    id = 6,
                    title = "해리 포터와 죽음의 성물 1부",
                    posterResource = "harry_potter_7",
                    screeningStartDate = LocalDate.of(2025, 5, 13),
                    screeningEndDate = LocalDate.of(2025, 6, 20),
                    runningTime = 157,
                ),
                Movie(
                    id = 7,
                    title = "해리 포터와 죽음의 성물 2부",
                    posterResource = "harry_potter_8",
                    screeningStartDate = LocalDate.of(2025, 5, 15),
                    screeningEndDate = LocalDate.of(2025, 6, 22),
                    runningTime = 157,
                ),
            )

        movieList.forEach { movie -> movies[movie.id] = movie }
    }

    operator fun get(id: Int): Movie = movies[id] ?: throw IllegalStateException(NOT_FOUND_MOVIE)

    fun getAll(): List<Movie> = movies.values.toList()

    companion object {
        private const val NOT_FOUND_MOVIE = "영화를 찾을 수 없습니다."
    }
}
