package woowacourse.movie.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.booking.AdmissionCount

class AdmissionCountTest {
    @Test
    fun `인원수가 한명에서 두명으로 증가한다`() {
        // given
        val admissionCount = AdmissionCount(1)

        // when
        val expected = admissionCount.increase(10).value

        // then
        assertEquals(expected, 2)
    }

    @Test
    fun `인원수가 두명에서 한명으로 감소한다`() {
        // given
        val admissionCount = AdmissionCount(2)

        // when
        val expected = admissionCount.decrease().value

        // then
        assertEquals(expected, 1)
    }

    @Test
    fun `인원수가 한명보다 적으면 감소되지 않는다`() {
        // given
        val admissionCount = AdmissionCount(1)

        // when
        val expected = admissionCount.decrease().value

        // then
        assertEquals(expected, AdmissionCount(1).value)
    }
}
