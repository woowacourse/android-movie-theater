package woowacourse.movie.domain

import woowacourse.movie.domain.movietime.Date
import java.io.Serializable
import java.time.LocalDate

data class Movie(
    val movieId: Int,
    val title: String,
    val date: Date,
    val time: Int,
) : Serializable {
    companion object {
        val dummy: List<Movie> =
            listOf(
                Movie(
                    1,
                    "해리포터",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
                Movie(
                    2,
                    "스즈메의 문단속",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
                Movie(
                    3,
                    "범죄도시",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
                Movie(
                    4,
                    "김씨표류기",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
                Movie(
                    5,
                    "싱스트리트",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
                Movie(
                    6,
                    "어거스트 러쉬",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
            )
    }
}
