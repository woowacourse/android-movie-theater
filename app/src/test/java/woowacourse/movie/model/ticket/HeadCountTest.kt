package woowacourse.movie.model.ticket

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.model.result.Failure

class HeadCountTest {
    @Test
    fun `플러스 버튼을 누르면 인원 수가 1 증가한다 `() {
        val headCount = HeadCount()

        headCount.increase()

        assertThat(headCount.count).isEqualTo(2)
    }

    @Test
    fun `마이너스 버튼을 누르면 인원 수가 1 감소한다 `() {
        val headCount = HeadCount()

        headCount.increase()
        headCount.decrease()

        assertThat(headCount.count).isEqualTo(1)
    }

    @Test
    fun `인원 수가 1일 때 마이너스 버튼을 누르면 Failure가 반환된다`() {
        val headCount = HeadCount()

        val actual = headCount.decrease()

        assertThat(actual).isInstanceOf(Failure::class.java)
    }

    @Test
    fun `인원 수가 20일 때 플러스 버튼을 누르면 Failure가 반환된다`() {
        val headCount = HeadCount()

        repeat(19) {
            headCount.increase()
        }

        val actual = headCount.increase()

        assertThat(actual).isInstanceOf(Failure::class.java)
    }
}
