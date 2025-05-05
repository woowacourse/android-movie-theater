package woowacourse.movie.domain.model.seat

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SeatGradeTest {
    @Test
    fun `1행, 2행의 좌석은 B등급이다`() {
        // given
        val seat1 = 0
        val seat2 = 1

        // when
        val grade1 = SeatGrade.of(seat1)
        val grade2 = SeatGrade.of(seat2)

        // then
        assertSoftly {
            grade1 shouldBe SeatGrade.B
            grade2 shouldBe SeatGrade.B
        }
    }

    @Test
    fun `3행, 4행의 좌석은 S등급이다`() {
        // given
        val seat1 = 2
        val seat2 = 3

        // when
        val grade1 = SeatGrade.of(seat1)
        val grade2 = SeatGrade.of(seat2)

        // then
        assertSoftly {
            grade1 shouldBe SeatGrade.S
            grade2 shouldBe SeatGrade.S
        }
    }

    @Test
    fun `5행의 좌석은 A등급이다`() {
        // given
        val seat = 4

        // when
        val grade = SeatGrade.of(seat)

        // then
        grade shouldBe SeatGrade.A
    }

    @Test
    fun `유효하지 않은 행 번호는 예외를 발생시킨다`() {
        val invalidRow = 5
        assertThrows<IllegalArgumentException> { SeatGrade.of(invalidRow) }
    }

    @Test
    fun `B등급 좌석의 가격은 10000원이다`() {
        // when
        val seatB = SeatGrade.B

        // then
        seatB.price shouldBe 10000
    }

    @Test
    fun `A등급 좌석의 가격은 12000원이다`() {
        // when
        val seatA = SeatGrade.A

        // then
        seatA.price shouldBe 12000
    }

    @Test
    fun `S등급 좌석의 가격은 15000원이다`() {
        // when
        val seatS = SeatGrade.S

        // then
        seatS.price shouldBe 15000
    }
}
