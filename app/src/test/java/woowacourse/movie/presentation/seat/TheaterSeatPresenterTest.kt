package woowacourse.movie.presentation.seat

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.Cinema

@ExtendWith(MockKExtension::class)
class TheaterSeatPresenterTest {
    @RelaxedMockK
    private lateinit var view: TheaterSeatContract.View

    @RelaxedMockK
    private lateinit var cinema: Cinema

    @MockK
    private lateinit var repository: MovieRepository

    private var ticketLimit: Int = 2
    private var dateTime: String = "2024.05.01 10:00"

    @InjectMockKs
    private lateinit var presenter: TheaterSeatPresenter

    @Test
    fun `좌석 선택 시 배경색 변경 테스트`() {
        // given
        val seatId = "A1"
        val expectedColor = "#FF0000"
        val colorSlot = slot<String>()
        every { view.setSeatBackground(seatId, capture(colorSlot)) } just runs
        // when
        presenter.toggleSeatSelection(seatId)
        // then
        colorSlot.captured shouldBe expectedColor
    }

    @Test
    fun `좌석 선택시 가격 적용 테스트`() {
        // given
        val seatId = "A1"
        val expectedPrice = 10_000
        every { view.showPrice(expectedPrice) } just runs
        // when
        presenter.toggleSeatSelection(seatId)
        // then
        verify(exactly = 1) { view.showPrice(expectedPrice) }
    }

    @Test
    fun `티켓수가 2일 때, 좌석은 2만큼 선택할 수 있다`() {
        // given
        val seatId1 = "A1"
        val seatId2 = "A2"
        val redColor = "#FF0000"
        val seatColorSlot = mutableListOf<String>()
        every { view.setSeatBackground(seatId1, capture(seatColorSlot)) } just runs
        every { view.setSeatBackground(seatId2, capture(seatColorSlot)) } just runs
        // when
        presenter.toggleSeatSelection(seatId1)
        presenter.toggleSeatSelection(seatId2)
        // then
        assertSoftly {
            seatColorSlot[0] shouldBe redColor
            seatColorSlot[1] shouldBe redColor
        }
    }

    @Test
    fun `티켓수가 2개 일 때, 좌석은 3개 이상 선택할 수 없다`() {
        // given
        val seatId1 = "A1"
        val seatId2 = "A2"
        val seatId3 = "A3"
        val redColor = "#FF0000"
        every { view.setSeatBackground(seatId1, redColor) } just runs
        every { view.setSeatBackground(seatId2, redColor) } just runs
        every { view.setSeatBackground(seatId3, redColor) } just runs
        // when
        presenter.toggleSeatSelection(seatId1)
        presenter.toggleSeatSelection(seatId2)
        presenter.toggleSeatSelection(seatId3)
        // then
        verify(exactly = 1) { view.setSeatBackground(seatId1, redColor) }
        verify(exactly = 1) { view.setSeatBackground(seatId2, redColor) }
        verify(exactly = 0) { view.setSeatBackground(seatId3, redColor) }
    }

    @Test
    fun `빈 좌석 선택하고, 재선택하면 빈 좌석으로 돌아가는지 테스트`() {
        // given
        val seatId = "A1"
        val whiteColor = "#FFFFFF"
        val redColor = "#FF0000"
        val colorSlot = mutableListOf<String>()
        every { view.setSeatBackground(seatId, capture(colorSlot)) } just runs
        // when
        presenter.toggleSeatSelection(seatId) // Select
        presenter.toggleSeatSelection(seatId) // Deselect
        // then
        assertSoftly {
            colorSlot[0] shouldBe redColor
            colorSlot[1] shouldBe whiteColor
        }
    }
}
