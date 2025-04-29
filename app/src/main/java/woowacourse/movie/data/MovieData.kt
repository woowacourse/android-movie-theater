package woowacourse.movie.data

import woowacourse.movie.domain.model.movie.Movie
import java.time.LocalDate

object MovieData {
    fun getData(): List<Movie> = listOf(movie1, movie2, movie3, movie4, movie5, movie6, movie7)

    val movie1 = Movie(
        "승부",
        LocalDate.of(2025, 3, 26),
        LocalDate.of(2025, 5, 26),
        115,
    )
    val movie2 = Movie(
        "미키 17",
        LocalDate.of(2025, 4, 1),
        LocalDate.of(2025, 5, 13),
        137,
    )
    val movie3 = Movie(
        "야당",
        LocalDate.of(2025, 4, 1),
        LocalDate.of(2025, 6, 1),
        123,
    )
    val movie4 = Movie(
        "위플래쉬",
        LocalDate.of(2025, 3, 12),
        LocalDate.of(2025, 5, 31),
        106,
    )
    val movie5 = Movie(
        "고독한 미식가 더 무비",
        LocalDate.of(2025, 3, 19),
        LocalDate.of(2025, 5, 8),
        110,
    )
    val movie6 = Movie(
        "너의 췌장을 먹고 싶어",
        LocalDate.of(2025, 4, 9),
        LocalDate.of(2025, 6, 1),
        115,
    )
    val movie7 = Movie(
        "로비",
        LocalDate.of(2025, 4, 2),
        LocalDate.of(2025, 5, 30),
        106,
    )
}
