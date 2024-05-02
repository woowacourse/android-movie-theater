package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.repository.FakeScreenRepository

class TheaterTest {
    @Test
    fun `상영작이 영화관에서 상영하는지 판단`() {
        val theater =
            Theater(
                1,
                "선릉 극장",
                listOf(FakeScreenRepository.fakeScreen1, FakeScreenRepository.fakeScreen2, FakeScreenRepository.fakeScreen3),
            )

        val screen = FakeScreenRepository.fakeScreen1
        val actual = theater.isScreening(screen)

        assertThat(actual).isTrue()
    }

    @Test
    fun `상영작이 상영하는 시간을 모두 계산한다`() {
        val theater =
            Theater(
                1,
                "선릉 극장",
                listOf(
                    FakeScreenRepository.fakeScreen1,
                    FakeScreenRepository.fakeScreen2,
                    FakeScreenRepository.fakeScreen3,
                ),
            )

        val screen = FakeScreenRepository.fakeScreen1
        val actual = theater.allScreeningTimeCount(screen)

        assertThat(actual).isEqualTo(22)
    }
}
