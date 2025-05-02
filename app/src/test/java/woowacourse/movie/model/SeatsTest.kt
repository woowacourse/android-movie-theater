package woowacourse.movie.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_A2
import woowacourse.movie.fixture.SEAT_C1
import woowacourse.movie.fixture.SEAT_E1
import kotlin.test.assertFalse

class SeatsTest {
    @Test
    fun `선택된 좌석별 등급에 따라 총 예매가격을 계산한다`() {
        val seats =
            Seats(
                mutableSetOf(
                    SEAT_E1,
                    SEAT_A1,
                    SEAT_C1,
                ),
            )

        val expected = 37000
        assertEquals(expected, seats.amount)
    }

    @Test
    fun `해당 좌석을 이미 선택한 상태라면 true를 반환한다`() {
        val seats =
            Seats(
                mutableSetOf(
                    SEAT_A1,
                ),
            )

        val actual = seats.has(SEAT_A1)

        assertTrue(actual)
    }

    @Test
    fun `해당 좌석이 선택되지 않은 상태라면 false를 반환한다`() {
        val seats =
            Seats(
                mutableSetOf(
                    SEAT_A1,
                ),
            )

        val actual = seats.has(SEAT_A2)

        assertFalse(actual)
    }
}
