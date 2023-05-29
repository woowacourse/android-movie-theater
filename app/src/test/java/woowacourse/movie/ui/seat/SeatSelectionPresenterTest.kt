package woowacourse.movie.ui.seat

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.repository.FakeAlarmStateRepository
import woowacourse.movie.repository.FakeReservationRepository
import woowacourse.movie.uimodel.MovieTicketInfoModel
import woowacourse.movie.uimodel.PeopleCountModel
import woowacourse.movie.uimodel.SeatModel
import woowacourse.movie.uimodel.SelectedSeatsModel
import java.time.LocalDateTime

internal class SeatSelectionPresenterTest {
    private lateinit var presenter: SeatSelectionPresenter
    private lateinit var view: SeatSelectionContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)

        presenter = SeatSelectionPresenter(
            view,
            FakeAlarmStateRepository,
            FakeReservationRepository,
            MovieTicketInfoModel(
                "title",
                LocalDateTime.of(2023, 4, 30, 15, 0),
                PeopleCountModel(1),
            ),
        )
    }

    @Test
    fun 선택된_좌석_정보가_업데이트_되면_가격이_수정된다() {
        val slot = slot<Int>()
        every { view.updatePriceText(capture(slot)) } just Runs

        presenter.updateSelectedSeatsModel(
            SelectedSeatsModel(setOf(SeatModel(1, 1))),
        )

        val actual = slot.captured
        assertEquals(9000, actual)
        verify { view.updatePriceText(actual) }
    }

    @Test
    fun 좌석을_선택하면_좌석이_선택된다() {
        presenter.updateSeats(SeatModel(1, 1), false)

        assertEquals(1, presenter.selectedSeatsModel.seats.size)
    }

    @Test
    fun 선택된_좌석을_선택하면_좌석이_선택_취소된다() {
        presenter.updateSelectedSeatsModel(
            SelectedSeatsModel(setOf(SeatModel(1, 1))),
        )

        presenter.updateSeats(SeatModel(1, 1), true)

        assertEquals(0, presenter.selectedSeatsModel.seats.size)
    }

    @Test
    fun 좌석이_모두_선택된_경우_추가로_좌석을_선택하면_오류_메시지를_보여준다() {
        presenter.updateSelectedSeatsModel(
            SelectedSeatsModel(setOf(SeatModel(1, 1))),
        )

        presenter.updateSeats(SeatModel(1, 2), false)

        verify { view.showErrorMessage() }
    }
}
