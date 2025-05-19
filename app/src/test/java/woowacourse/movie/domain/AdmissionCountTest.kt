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
        val expected = admissionCount.increase(20).value

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
    fun `인원수는 1명 밑으로 감소하지 않는다`() {
        // given
        val admissionCount = AdmissionCount(1)

        // when
        val expected = admissionCount.decrease().value

        // then
        assertEquals(expected, AdmissionCount(1).value)
    }

    @Test
    fun `인원수는 20명 위로 증가하지 않는다`() {
        // given
        val admissionCount = AdmissionCount(20)

        // when
        val expected = admissionCount.increase(20).value

        // then
        assertEquals(expected, AdmissionCount(20).value)
    }
}
