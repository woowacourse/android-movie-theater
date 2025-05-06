package woowacourse.movie.domain.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class HeadCountTest {
    @Test
    fun `인원 수의 초기값은 1이다`() {
        // Given
        val headCount = HeadCount.of()

        // Then
        headCount.value shouldBe 1
    }

    @Test
    fun `1 미만의 값이 저장될 경우 인원 수는 1이 된다`() {
        // Given
        val wrongHeadCount = HeadCount.of(0)

        // Then
        wrongHeadCount.value shouldBe 1
    }

    @Test
    fun `increase()를 호출하면 인원 수가 1 증가한다`() {
        // Given
        val headCount = HeadCount.of()

        // When
        val newHeadCount = headCount.increase()

        // Then
        newHeadCount.value shouldBe 2
    }

    @Test
    fun `인원 수가 1보다 클 때 decrease()를 호출하면 인원 수가 1 감소한다`() {
        // Given
        val headCount = HeadCount.of()
        val increasedHeadCount = headCount.increase()

        // When
        val decreasedHeadCount = increasedHeadCount.decrease()

        // Then
        decreasedHeadCount.value shouldBe 1
    }

    @Test
    fun `인원 수가 1 이하일 때 decrease()를 호출하면 인원 수가 감소하지 않는다`() {
        // Given
        val headCount = HeadCount.of()

        // When
        val decreasedHeadCount = headCount.decrease()

        // Then
        decreasedHeadCount.value shouldBe 1
    }
}
