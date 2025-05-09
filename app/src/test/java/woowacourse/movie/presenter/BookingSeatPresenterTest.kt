package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.movie.TicketType
import woowacourse.movie.domain.model.theater.Seat
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.ui.seat.contract.BookingSeatContract
import woowacourse.movie.ui.seat.presenter.BookingSeatPresenter

class BookingSeatPresenterTest {
    private lateinit var view: BookingSeatContract.View
    private lateinit var presenter: BookingSeatPresenter

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = BookingSeatPresenter(view)
        presenter.loadState(
            theater = Theater(),
            headcount = Headcount(2),
            title = "",
        )
    }

    @Test
    fun `영화 정보를 업데이트하면 뷰에 반영된다`() {
        presenter.updateViews()
        verify { view.setTotalPrice(any()) }
        verify { view.setMovieTitle(any()) }
    }

    @Test
    fun `확인 버튼을 업데이트하면 활성화되거나 비활성화된다`() {
        presenter.refreshConfirmButton()
        verify { view.setConfirmButton(any()) }
    }

    @Test
    fun `좌석을 선택하면 좌석이 추가되고 View가 업데이트 된다`() {
        val targetSeat = Seat(0, 0)
        presenter.selectSeat(targetSeat)
        verify { view.toggleSeat(targetSeat, true) }
        verify { view.setTotalPrice(any()) }
        verify { view.setConfirmButton(any()) }
    }

    @Test
    fun `선택한 좌석 수가 인원 수와 다르면 확인 버튼이 비활성화된다`() {
        val targetSeat = Seat(0, 0)
        presenter.selectSeat(targetSeat)
        // Headcount는 2이지만, 좌석이 아직 하나만 선택되었다.
        verify { view.setConfirmButton(false) }
    }

    @Test
    fun `선택한 좌석 수가 인원 수와 같으면 확인 버튼이 활성화된다`() {
        presenter.selectSeat(Seat(0, 0))
        presenter.selectSeat(Seat(0, 1))
        verify { view.setConfirmButton(true) }
    }

    @Test
    fun `좌석을 클릭할 때마다 가격이 누적되어 10000, 20000원 순으로 반영된다`() {
        val priceSlots = mutableListOf<Int>()

        val bGradeSeat = Seat(0, 0, TicketType.B_GRADE)
        val sGradeSeat = Seat(3, 3, TicketType.S_GRADE)

        presenter.selectSeat(bGradeSeat)
        presenter.selectSeat(sGradeSeat)

        verify(exactly = 2) {
            view.setTotalPrice(capture(priceSlots))
        }

        assert(priceSlots == listOf(10000, 25000)) {
            "가격 누적 순서가 예상과 다릅니다: $priceSlots"
        }
    }
}
