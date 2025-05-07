package woowacourse.movie.domain.model.seat

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SeatTest {
    @Test
    fun `위치와 등급을 가진다`() {
        // Given
        val seat = Seat.of(1, 1)

        // Then
        assertSoftly(seat) {
            seatPosition shouldBe seatPosition
            grade shouldBe SeatGrade.B
        }
    }
}
