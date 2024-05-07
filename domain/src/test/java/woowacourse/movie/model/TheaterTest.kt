package woowacourse.movie.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TheaterTest {
    @Test
    fun `상영관의 1행은 S등급, 2행은 B등급, 열의 길이가 2일 때 그에 따른 좌석들을 생성한다`() {
        val theater =
            Theater(0, mapOf(Tier.S to listOf(1), Tier.A to listOf(2)), 2, "잠실")
        val seats = theater.seats()
        assertThat(seats).contains(
            Seat(Tier.S, 0, 0),
            Seat(Tier.S, 0, 1),
            Seat(Tier.A, 1, 0),
            Seat(Tier.A, 1, 1),
        )
    }
}
