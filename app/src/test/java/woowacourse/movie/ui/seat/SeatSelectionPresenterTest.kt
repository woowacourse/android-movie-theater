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
        view = mockk()
        every { view.initMovieTitleView("title") } just Runs
        every { view.updatePriceText(0) } just Runs
        every { view.updateButtonEnablement(false) } just Runs

        presenter = SeatSelectionPresenter(
            view,
            FakeAlarmStateRepository,
            FakeReservationRepository,
            MovieTicketInfoModel(
                "title",
                LocalDateTime.of(2023, 4, 30, 15, 0),
                PeopleCountModel(2),
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
/*
    @Test
    fun 좌석을_선택하면_선택된_좌석_목록에_해당_좌석이_추가된다() {
        val seat = SeatModel(1, 1)
        every { presenter.clickSeat(seat, any()) } just Runs

//        println(presenter.selectedSeatsModel.seats.toString())
        presenter.clickSeat(seat, true)

        assertEquals(true, presenter.isSelected(seat))
        verify { view.updatePriceText(9000) }
    }

    @Test
    fun 좌석을_선택하면_선택된_좌석_목록에_해당_좌석이_삭제된다() {
        val seat = SeatModel(1, 1)
        every { presenter.clickSeat(seat, false) } just Runs

//        println(presenter.selectedSeatsModel.seats.toString())
        presenter.clickSeat(seat, false)

        assertEquals(false, presenter.isSelected(seat))
        verify { view.updatePriceText(0) }
    }
*/
}
