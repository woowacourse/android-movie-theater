package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SeatsTest {
    @Test
    fun `좌석들의 총 가격을 구한다`() {
        val seats =
            Seats(
                Seat(Position(0, 0), Grade.S),
                Seat(Position(0, 1), Grade.A),
            )

        assertThat(seats.totalPrice()).isEqualTo(27_000)
    }

    @Test
    fun `좌석의 갯수를 센다`() {
        val seats =
            Seats(
                Seat(Position(0, 0), Grade.S),
                Seat(Position(0, 1), Grade.A),
            )
        assertThat(seats.count()).isEqualTo(2)
    }

    @Test
    fun `제일 긴 행의 길이를 구한다`() {
        val seats =
            Seats(
                Seat(Position(0, 0), Grade.S),
                Seat(Position(1, 1), Grade.A),
            )
        assertThat(seats.maxRow()).isEqualTo(2)
    }

    @Test
    fun `제일 긴 열으 길이를 구한다`() {
        val seats =
            Seats(
                Seat(Position(0, 0), Grade.S),
                Seat(Position(1, 1), Grade.A),
            )
        assertThat(seats.maxColumn()).isEqualTo(2)
    }

    @Test
    fun `위치를 가지고 좌석을 찾는다`() {
        val seats =
            Seats(
                Seat(Position(0, 0), Grade.S),
                Seat(Position(1, 1), Grade.A),
            )
        val actual = seats.findSeat(Position(1, 1))

        assertThat(actual).isEqualTo(Seat(Position(1, 1), Grade.A))
    }
}
