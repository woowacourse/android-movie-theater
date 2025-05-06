package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.ui.seat.BookingSeatContract
import woowacourse.movie.ui.seat.BookingSeatPresenter

class BookingSeatPresenterTest {
    private lateinit var view: BookingSeatContract.View
    private lateinit var presenter: BookingSeatPresenter

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = BookingSeatPresenter(view)
        presenter.loadBookingSeatInfo(
            1L, MovieSchedule(LocalDateTime.of(2025, 5, 7, 10, 0)),
            Headcount(2), "선릉 극장"
        )
    }

    @Test
    fun `확인 버튼을 업데이트하면 활성화되거나 비활성화된다`() {
        presenter.updateConfirmButton()
        verify { view.showConfirmButton(any()) }
    }

    @Test
    fun `좌석을 선택하면 좌석이 추가되고 View가 업데이트 된다`() {
        presenter.updateSeat("A1")
        verify { view.showSeatView(Seat.fromSeatTag("A1"), true) }
        verify { view.showTotalPrice(any()) }
        verify { view.showConfirmButton(any()) }
    }

    @Test
    fun `선택한 좌석 수가 인원 수와 다르면 확인 버튼이 비활성화된다`() {
        presenter.updateSeat("A1")
        // Headcount는 2이지만, 좌석이 아직 하나만 선택되었다.
        presenter.updateConfirmButton()
        verify { view.showConfirmButton(false) }
    }

    @Test
    fun `선택한 좌석 수가 인원 수와 같으면 확인 버튼이 활성화된다`() {
        presenter.updateSeat("A1")
        presenter.updateSeat("A2")
        presenter.updateConfirmButton()
        verify { view.showConfirmButton(true) }
    }
}
