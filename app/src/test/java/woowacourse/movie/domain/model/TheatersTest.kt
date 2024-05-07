package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.repository.FakeScreenRepository

class TheatersTest {
    @Test
    fun `상영작을 상영하고 있는 극장을 불러온다`() {
        val theaters =
            Theaters(
                Theater(1, "선릉", listOf(FakeScreenRepository.fakeScreen1, FakeScreenRepository.fakeScreen2)),
                Theater(2, "강남", listOf(FakeScreenRepository.fakeScreen2, FakeScreenRepository.fakeScreen3)),
            )

        val screen = FakeScreenRepository.fakeScreen2
        val actual = theaters.screeningTheater(screen)

        assertThat(actual).isEqualTo(theaters)
    }
}
