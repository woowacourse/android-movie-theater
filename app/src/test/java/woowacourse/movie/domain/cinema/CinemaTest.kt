package woowacourse.movie.domain.cinema

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.reservation.Movie
import woowacourse.movie.domain.reservation.Screening
import java.time.LocalDate
import java.time.LocalTime

class CinemaTest {
    private lateinit var cinema: Cinema
    private lateinit var screening: Screening

    @BeforeEach
    fun setUp() {
        screening =
            Screening(
                Movie(
                    1,
                    "해리 포터와 비밀의 방",
                    162,
                ),
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 4, 28),
            )
        cinema =
            Cinema(
                "선릉 극장",
                listOf(screening),
                showtimePolicy = { _ -> listOf(LocalTime.of(22, 0)) },
            )
    }

    @Test
    fun `극장별로 상영하는 영화는 달라질 수 있다`() {
        // when
        val screenings = cinema.screenings

        // then
        assertThat(screenings).isEqualTo(listOf(screening))
    }

    @Test
    fun `극장은 상영 시간들을 제공한다`() {
        // when
        val showtimes: List<LocalTime> = cinema.showtimes(screening, LocalDate.of(2025, 4, 2))

        // then
        assertThat(showtimes).isEqualTo(listOf(LocalTime.of(22, 0)))
    }
}
