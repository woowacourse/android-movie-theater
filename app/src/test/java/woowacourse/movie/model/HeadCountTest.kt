package woowacourse.movie.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class HeadCountTest {
    @Test
    fun `인원수가 1명인 경우에 minus를 호출해도 인원수가 줄어들지 않는다`() {
        // given
        val headCount = HeadCount(0)

        // when
        val actual = headCount.minus()
        val expected = HeadCount(0)

        // given
        assertEquals(actual, expected)
    }

    @ParameterizedTest
    @ValueSource(ints = [2, 4, 10, 25])
    fun `인원수가 2명 이상인 경우에 minus를 호출하면 인원수가 줄어든다`(count: Int) {
        // given
        val headCount = HeadCount(count)

        // when
        val actual = headCount.minus()
        val expected = HeadCount(count - 1)

        // then
        assertEquals(actual, expected)
    }

    @Test
    fun `plus를 호출하면 인원수가 증가한다`() {
        // given
        val headCount = HeadCount(1)

        // when
        val actual = headCount.plus()
        val expected = HeadCount(2)

        // then
        assertEquals(actual, expected)
    }
}
