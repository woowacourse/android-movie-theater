package woowacourse.movie.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.movie.domain.seat.BookingSeats
import woowacourse.movie.domain.seat.Column
import woowacourse.movie.domain.seat.Row
import woowacourse.movie.domain.seat.Seat
import woowacourse.movie.domain.seat.SeatGrade

class MemberCountTest {
    @ParameterizedTest
    @ValueSource(ints = [-1, 0, -100])
    fun 예매_가능한_인원_수는_1명_이상이다(count: Int) {
        assertThrows<IllegalArgumentException> {
            BookingSeats(count)
        }
    }

    @ParameterizedTest
    @CsvSource(value = ["1,15000", "2,30000"])
    fun 인원에따라_예매_가격을_계산할_수_있다(
        count: Int,
        price: Int,
    ) {
        val seat = BookingSeats(count)
        if (count == 1 ) {
            seat.add(Seat(Row(1), Column(1), SeatGrade.S))
        } else {
            seat.add(Seat(Row(1), Column(1), SeatGrade.S))
            seat.add(Seat(Row(1), Column(1), SeatGrade.S))
        }

        val actual = seat.calculateTicketPrices()

        assertThat(actual).isEqualTo(price)
    }
}
