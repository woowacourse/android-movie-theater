package woowacourse.movie.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MovieTheaterTest {
    @Test
    fun `상영관의 1행은 S등급, 2행은 B등급, 열의 길이가 2일 때 그에 따른 좌석들을 생성한다`() {
        val theater =
            MovieTheater(0, "잠실", MovieTheater.seatSystem.seats())
        val seats = theater.seats
        assertThat(seats).contains(
            Seat(SeatRate.S, 3, 0),
            Seat(SeatRate.S, 3, 1),
            Seat(SeatRate.A, 4, 0),
            Seat(SeatRate.A, 4, 1),
        )
    }
}
