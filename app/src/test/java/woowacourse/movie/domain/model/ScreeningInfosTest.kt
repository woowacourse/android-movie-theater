package woowacourse.movie.domain.model

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.movie.Movie
import java.time.LocalDate
import java.time.LocalTime

class ScreeningInfosTest {
    @Test
    fun `해당 영화의 상영 정보를 반환한다`() {
        // Given
        val testMovie = Movie(
            "Test",
            LocalDate.of(2025, 4, 30),
            LocalDate.of(2025, 5, 1),
            100
        )
        val data = listOf(
            ScreeningInfo(
                "선릉 극장",
                testMovie,
                listOf(LocalTime.of(12, 0))
            )
        )
        val screeningInfos = ScreeningInfos(data)

        // When
        val expected = screeningInfos.findByMovie(testMovie)

        // Then
        assertSoftly(expected.first()) {
            theater shouldBe "선릉 극장"
            movie shouldBe testMovie
            times shouldBe listOf(LocalTime.of(12, 0))
        }
    }
}