package woowacourse.movie.domain.model.movie

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MovieTicketTest {
    private lateinit var movieTicket: MovieTicket

    @BeforeEach
    fun setUp() {
        movieTicket =
            MovieTicket(
                "승부",
                "선릉 극장",
                LocalDateTime.of(2025, 5, 30, 11, 0),
                3,
            )
    }

    @Test
    fun `제목, 상영날짜, 인원을 가진다`() {
        // Then
        assertSoftly(movieTicket) {
            movieTitle shouldBe "승부"
            screeningDateTime shouldBe LocalDateTime.of(2025, 5, 30, 11, 0)
            headCount shouldBe 3
        }
    }
}
