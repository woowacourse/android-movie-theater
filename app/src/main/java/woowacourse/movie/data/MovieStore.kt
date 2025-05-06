package woowacourse.movie.data

import woowacourse.movie.domain.model.booking.ScreeningDates
import woowacourse.movie.domain.model.feed.Feed.Movie
import java.time.LocalDate

class MovieStore {
    private val baseDate = LocalDate.now()

    val movies: List<Movie> =
        listOf(
            Movie(
                0,
                "해리 포터와 마법사의 돌",
                "harry_potter_1",
                createDateRange(baseDate, -3, 7),
                152,
            ),
            Movie(
                1,
                "해리 포터와 비밀의 방",
                "harry_potter_2",
                createDateRange(baseDate, -5, 8),
                162,
            ),
            Movie(
                2,
                "해리 포터와 아즈카반의 죄수",
                "harry_potter_3",
                createDateRange(baseDate, -7, 2),
                141,
            ),
            Movie(
                3,
                "해리 포터와 불의 잔",
                "harry_potter_4",
                createDateRange(baseDate, -7, 4),
                157,
            ),
            Movie(
                4,
                "해리 포터와 불사조 기사단",
                "harry_potter_5",
                createDateRange(baseDate, -2, 11),
                157,
            ),
            Movie(
                5,
                "해리 포터와 혼혈 왕자",
                "harry_potter_6",
                createDateRange(baseDate, -8, 3),
                157,
            ),
            Movie(
                6,
                "해리 포터와 죽음의 성물 1부",
                "harry_potter_7",
                createDateRange(baseDate, -5, 5),
                157,
            ),
            Movie(
                7,
                "해리 포터와 죽음의 성물 2부",
                "harry_potter_8",
                createDateRange(baseDate, -5, 6),
                157,
            ),
        )

    private fun createDateRange(
        baseDate: LocalDate,
        left: Int,
        right: Int,
    ): ScreeningDates {
        val range =
            (left..right).map { offset ->
                baseDate.plusDays(offset.toLong())
            }
        return ScreeningDates(range)
    }
}
