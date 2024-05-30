package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.data.repository.FakeScreenDataSource

class TheaterTest {
    @Test
    fun `상영작이 영화관에서 상영하는지 판단`() {
        val theater =
            Theater(
                1,
                "선릉 극장",
                listOf(FakeScreenDataSource.fakeScreen1Data, FakeScreenDataSource.fakeScreen2Data, FakeScreenDataSource.fakeScreen3Data),
            )

        val screen = FakeScreenDataSource.fakeScreen1Data
        val actual = theater.hasScreen(screen)

        assertThat(actual).isTrue()
    }

    @Test
    fun `상영작이 상영하는 시간을 모두 계산한다`() {
        val theater =
            Theater(
                1,
                "선릉 극장",
                listOf(
                    FakeScreenDataSource.fakeScreen1Data,
                    FakeScreenDataSource.fakeScreen2Data,
                    FakeScreenDataSource.fakeScreen3Data,
                ),
            )

        val screen = FakeScreenDataSource.fakeScreen1Data
        val actual = theater.allScreeningTimeCount(screen)

        assertThat(actual).isEqualTo(22)
    }
}
