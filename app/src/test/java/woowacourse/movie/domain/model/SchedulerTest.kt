package woowacourse.movie.domain.model

import io.kotest.assertions.assertSoftly
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.HARRY_POTTER
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SchedulerTest {
    private lateinit var scheduler: Scheduler

    @BeforeEach
    fun setUp() {
        scheduler =
            Scheduler(
                Screening(
                    "선릉 극장",
                    HARRY_POTTER,
                    listOf(1, 2, 20, 22).map { LocalTime.of(it, 0) },
                ),
            )
    }

    @Test
    fun `영화 상영 기간에 포함되며, 오늘 이후의 예매 가능한 날짜들을 반환한다`() {
        // given
        val today = LocalDate.of(2025, 5, 5)

        // when
        val bookableDates = scheduler.getBookableDates(today)

        // then
        assertSoftly(bookableDates) {
            shouldNotBeEmpty()
            forAll { bookableDate ->
                !bookableDate.isBefore(today) shouldBe true
            }
        }
    }

    @Test
    fun `선택할 상영일이 오늘이면 현재 이후의 예매 가능한 시간들을 반환한다`() {
        // given
        val today = LocalDate.of(2025, 5, 5)
        val time = LocalTime.of(10, 0)
        val now = LocalDateTime.of(today, time)

        // when
        val bookableTimes = scheduler.getBookableTimes(today, now)

        // then
        assertSoftly(bookableTimes) {
            shouldNotBeEmpty()
            forAll { bookableTime ->
                !bookableTime.isBefore(time) shouldBe true
            }
        }
    }
}
