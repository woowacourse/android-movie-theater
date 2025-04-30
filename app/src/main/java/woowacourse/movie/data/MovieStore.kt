package woowacourse.movie.data

import woowacourse.movie.domain.model.booking.ScreeningDate
import woowacourse.movie.domain.model.movies.Movie
import java.time.LocalDate

class MovieStore {
    val movies: MutableMap<Int, Movie> = mutableMapOf()

    init {
        initData()
    }

    private fun initData() {
        val movieList =
            listOf(
                Movie(
                    0,
                    "해리 포터와 마법사의 돌",
                    "harry_potter_1",
                    ScreeningDate(
                        LocalDate.of(2025, 5, 1),
                        LocalDate.of(2025, 5, 25),
                    ),
                    152,
                ),
                Movie(
                    1,
                    "해리 포터와 비밀의 방",
                    "harry_potter_2",
                    ScreeningDate(
                        LocalDate.of(2025, 5, 2),
                        LocalDate.of(2025, 5, 20),
                    ),
                    162,
                ),
                Movie(
                    2,
                    "해리 포터와 아즈카반의 죄수",
                    "harry_potter_3",
                    ScreeningDate(LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 20)),
                    141,
                ),
                Movie(
                    3,
                    "해리 포터와 불의 잔",
                    "harry_potter_4",
                    ScreeningDate(LocalDate.of(2025, 5, 5), LocalDate.of(2025, 5, 14)),
                    157,
                ),
                Movie(
                    4,
                    "해리 포터와 불사조 기사단",
                    "harry_potter_5",
                    ScreeningDate(LocalDate.of(2025, 5, 7), LocalDate.of(2025, 5, 20)),
                    157,
                ),
                Movie(
                    5,
                    "해리 포터와 혼혈 왕자",
                    "harry_potter_6",
                    ScreeningDate(LocalDate.of(2025, 5, 2), LocalDate.of(2025, 5, 29)),
                    157,
                ),
                Movie(
                    6,
                    "해리 포터와 죽음의 성물 1부",
                    "harry_potter_7",
                    ScreeningDate(LocalDate.of(2025, 5, 20), LocalDate.of(2025, 5, 29)),
                    157,
                ),
                Movie(
                    7,
                    "해리 포터와 죽음의 성물 2부",
                    "harry_potter_8",
                    ScreeningDate(LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 29)),
                    157,
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
