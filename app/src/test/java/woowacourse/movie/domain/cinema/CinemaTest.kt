package woowacourse.movie.domain.cinema

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.reservation.Movie
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy
import java.time.LocalDate
import java.time.LocalTime

class Cinema(
    private val showtimePolicy: ShowtimePolicy,
) {
    fun showtimes(
        screening: Screening,
        date: LocalDate,
    ): List<LocalTime> {
        val showtimes = screening.showtimes(date, showtimePolicy)
        return showtimes
    }
}

class CinemaTest {
    @Test
    fun `극장은 상영 시간들을 제공한다`() {
        // given
        val screening =
            Screening(
                Movie(
                    1,
                    "해리 포터와 비밀의 방",
                    162,
                ),
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 4, 28),
            )
        val cinema = Cinema(showtimePolicy = { _, _ -> listOf(LocalTime.of(22, 0)) })

        // when
        val showtimes: List<LocalTime> = cinema.showtimes(screening, LocalDate.of(2025, 4, 2))

        // then
        assertThat(showtimes).isEqualTo(listOf(LocalTime.of(22, 0)))
    }
}
