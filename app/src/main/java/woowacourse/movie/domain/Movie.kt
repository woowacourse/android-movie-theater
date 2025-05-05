package woowacourse.movie.domain

import woowacourse.movie.domain.movietime.Date
import java.io.Serializable
import java.time.LocalDate

data class Movie(
    val movieId: MovieId,
    val title: String,
    val date: Date,
    val time: Int,
) : Serializable {
    companion object {
        val dummy: List<Movie> =
            listOf(
                Movie(
                    MovieId.Harry1,
                    "해리포터",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
                Movie(
                    MovieId.Suzume,
                    "스즈메의 문단속",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
                Movie(
                    MovieId.CriminalCity3,
                    "범죄도시",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
                Movie(
                    MovieId.CastAway,
                    "김씨표류기",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
                Movie(
                    MovieId.StringStreet,
                    "싱스트리트",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
                Movie(
                    MovieId.Agustrush,
                    "어거스트 러쉬",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    152,
                ),
            )
    }
}
