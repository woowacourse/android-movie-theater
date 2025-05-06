package woowacourse.movie.domain.model

import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class TheaterTest {
    @ParameterizedTest
    @CsvSource(value = ["1,2", "2,0"])
    fun `극장은 영화id를 통해 해당 영화의 상영수를 알 수 있다`(movieId: String, expected: String) {
        val theater =
            Theater(
                name = "선릉 극장",
                theaterSchedules =
                    TheaterSchedules(
                        mutableMapOf(
                            1L to
                                    setOf(
                                        MovieSchedule(LocalDateTime.of(2025, 4, 10, 13, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 4, 15, 16, 30)),
                                    ),
                            2L to
                                    setOf()
                        ),
                    ),
            )

        val actual: Int = theater.scheduleCountByMovieId(movieId.toLong())

        assertThat(actual).isEqualTo(expected.toInt())
    }
}
