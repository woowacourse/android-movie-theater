package woowacourse.movie.data

import woowacourse.movie.domain.model.booking.ScreeningDates
import woowacourse.movie.domain.model.movies.Movie
import java.time.LocalDate

class MovieStore {
    val movies: MutableMap<Int, Movie> = mutableMapOf()

    init {
        initData()
    }

    private fun initData() {
        fun createScreeningDate(
            start: LocalDate,
            days: Long,
        ): ScreeningDates {
            val dates = (0 until days).map { start.plusDays(it) }
            return ScreeningDates(dates)
        }

        val baseDate = LocalDate.of(2025, 5, 1)

        val movieList =
            listOf(
                Movie(
                    0,
                    "해리 포터와 마법사의 돌",
                    "harry_potter_1",
                    createScreeningDate(baseDate, 3),
                    152,
                ),
                Movie(
                    1,
                    "해리 포터와 비밀의 방",
                    "harry_potter_2",
                    createScreeningDate(baseDate, 5),
                    162,
                ),
                Movie(
                    2,
                    "해리 포터와 아즈카반의 죄수",
                    "harry_potter_3",
                    createScreeningDate(baseDate, 4),
                    141,
                ),
                Movie(
                    3,
                    "해리 포터와 불의 잔",
                    "harry_potter_4",
                    createScreeningDate(baseDate, 6),
                    157,
                ),
                Movie(
                    4,
                    "해리 포터와 불사조 기사단",
                    "harry_potter_5",
                    createScreeningDate(baseDate, 7),
                    157,
                ),
                Movie(
                    5,
                    "해리 포터와 혼혈 왕자",
                    "harry_potter_6",
                    createScreeningDate(baseDate, 5),
                    157,
                ),
                Movie(
                    6,
                    "해리 포터와 죽음의 성물 1부",
                    "harry_potter_7",
                    createScreeningDate(baseDate, 4),
                    157,
                ),
                Movie(
                    7,
                    "해리 포터와 죽음의 성물 2부",
                    "harry_potter_8",
                    createScreeningDate(baseDate, 3),
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
