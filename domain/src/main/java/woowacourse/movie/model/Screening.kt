package woowacourse.movie.model

import java.time.LocalDateTime

class Screening(
    val id: Long,
    val movie: Movie,
    val theater: Theater,
    val localDateTime: LocalDateTime,
) {
    companion object {
        val STUB =
            Screening(
                1,
                Movie.STUB_A,
                Theater.STUB_A,
                LocalDateTime.of(2024, 3, 1, 9, 0),
            )
    }
}
