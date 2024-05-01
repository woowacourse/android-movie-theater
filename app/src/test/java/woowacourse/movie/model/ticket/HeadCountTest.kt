package woowacourse.movie.model.ticket

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.result.Failure

class HeadCountTest {
    @Test
    fun `플러스 버튼을 누르면 티켓 매수가 1장 증가한다 `() {
        val headCount = HeadCount()

        headCount.increase()

        assertThat(headCount.count).isEqualTo(2)
    }

    @Test
    fun `마이너스 버튼을 누르면 티켓 매수가 1장 감소한다 `() {
        val headCount = HeadCount()

        headCount.increase()
        headCount.decrease()

        assertThat(headCount.count).isEqualTo(1)
    }

    @Test
    fun `티켓이 1장일 때 마이너스 버튼을 누르면 Failure가 반환된다`() {
        val headCount = HeadCount()

        val actual = headCount.decrease()

        assertThat(actual).isInstanceOf(Failure::class.java)
    }

    @Test
    fun `티켓이 100장일 때 플러스 버튼을 누르면 Failure가 반환된다`() {
        val headCount = HeadCount()

        repeat(99) {
            headCount.increase()
        }

        val actual = headCount.increase()

        assertThat(actual).isInstanceOf(Failure::class.java)
    }
}
