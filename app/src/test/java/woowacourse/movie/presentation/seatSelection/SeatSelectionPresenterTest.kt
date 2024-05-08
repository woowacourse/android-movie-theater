package woowacourse.movie.presentation.seatSelection

import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.db.ReservationDatabase
import woowacourse.movie.repository.MovieRepository

@ExtendWith(MockKExtension::class)
class SeatSelectionPresenterTest {
    private lateinit var presenter: SeatSelectionPresenter

    private val ticketCount = 3

    @RelaxedMockK
    private lateinit var view: SeatSelectionContract.View

    @MockK
    private lateinit var repository: MovieRepository

    @RelaxedMockK
    private lateinit var db: ReservationDatabase

    @BeforeEach
    fun setUp() {
        presenter = SeatSelectionPresenter(view, ticketCount, db, repository)
    }

    @Test
    fun `updateSeatSelection로 전달 받은 index 값의 좌석 상태가 빈 좌석일 경우, 선택된 좌석 UI로 업데이트 하도록 view에게 요청한다`() {
        presenter.updateSeatSelection(0)

        verify { view.updateSelectedSeatUI(0) }
        verify { view.updateViews() }
    }

    @Test
    fun `updateSeatSelection으로 전달 받은 index 값의 좌석 상태가 이미 선택된 좌석일 경우, 좌석 기본 UI로 업데이트 하도록 view에게 요청한다`() {
        presenter.seatingSystem.trySelectSeat(0)
        presenter.updateSeatSelection(0)

        verify { view.updateUnSelectedSeatUI(0) }
    }
}
