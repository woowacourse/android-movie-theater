package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.model.Theater

class TheaterTest {
    @Test
    fun `영화관은 이름을 가진다`() {
        val theater = Theater("선릉", listOf(dummyMovie))
        val expectedName = "선릉"
        assertThat(theater.name).isEqualTo(expectedName)
    }
}
