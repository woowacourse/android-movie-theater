package woowacourse.movie.presenter

import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.SelectSeatContract
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TheatersUiModel
import woowacourse.movie.model.TicketOfficeUiModel
import woowacourse.movie.model.TicketsUiModel
import woowacourse.movie.sql.ReservationDbHelperInterface
import woowacourse.movie.view.SeatView
import java.time.LocalDate
import java.time.LocalDateTime

class SelectSeatPresenterTest {
    private lateinit var presenter: SelectSeatContract.Presenter
    private lateinit var view: SelectSeatContract.View
    private lateinit var movieUiModel: MovieUiModel
    private lateinit var reservationDb: ReservationDbHelperInterface

    @Before
    fun setUp() {
        view = mockk()
        movieUiModel =
            MovieUiModel(1, "", LocalDate.MAX, LocalDate.MIN, TheatersUiModel(listOf()), 0, "")
        reservationDb = mockk()
        val ticketOfficeUiModel = TicketOfficeUiModel(
            TicketsUiModel(listOf()),
            3,
            "선릉",
            LocalDateTime.of(2023, 5, 6, 11, 0)
        )
        presenter = SelectSeatPresenter(
            view,
            ticketOfficeUiModel,
            movieUiModel,
            reservationDb
        )
    }

    @Test
    fun 좌석을_누르면_좌석상태와_가격상태_버튼상태가_바뀐다_() {
        // given
        val seatView: SeatView = mockk()
        val slotTicketsUiModel = slot<TicketsUiModel>()
        val slotPrice = slot<Int>()
        val slotClickable = slot<Boolean>()
        val slotColorCondition = slot<Boolean>()
        every { seatView.row } returns 'A'
        every { seatView.col } returns 1
        every { view.updateSeats(capture(slotTicketsUiModel)) } just runs
        every { view.setPriceText(capture(slotPrice)) } just runs
        every { view.setCheckButtonClickable(capture(slotClickable)) } just runs
        every { view.setCheckButtonColor(capture(slotColorCondition)) } just runs
        // when
        presenter.onClickSeat(seatView)
        // then
        verify { view.updateSeats(slotTicketsUiModel.captured) }
        assertEquals(slotPrice.captured, 10000)
        verify { view.setPriceText(slotPrice.captured) }
        assertEquals(slotClickable.captured, false)
        verify { view.setCheckButtonClickable(slotClickable.captured) }
        assertEquals(slotColorCondition.captured, false)
        verify { view.setCheckButtonColor(slotColorCondition.captured) }
    }

    @Test
    fun 확인_버튼을_누르면_다이얼로그가_나온다() {
        // given
        every { view.showDialog() } just runs
        // when
        presenter.onClickCheckButton()
        // then
        verify { view.showDialog() }
    }

    @Test
    fun 다이얼로그_확인_버튼을_누르면_알림을_보내고_예약_결과창으로_넘어가고_데이터베이스에_저장한다() {
        // given
        val slotStartActivity = slot<TicketsUiModel>()
        val slotRegisterAlarm = slot<TicketsUiModel>()
        every { reservationDb.saveReservation(any()) } just runs
        every {
            view.startReservationResultActivity(
                movieUiModel,
                capture(slotStartActivity)
            )
        } just runs
        every { view.registerAlarm(movieUiModel, capture(slotRegisterAlarm)) } just runs
        // when
        presenter.onClickDialogPositiveButton()
        // then
        verify { view.startReservationResultActivity(movieUiModel, slotStartActivity.captured) }
        verify { view.registerAlarm(movieUiModel, slotRegisterAlarm.captured) }
        verify { reservationDb.saveReservation(any()) }
    }

    @Test
    fun 다이얼로그_취소_버튼을_누르면_다이얼로그가_꺼진다() {
        // given
        every { view.cancelDialog() } just runs
        // when
        presenter.onClickDialogCancelButton()
        // then
        verify { view.cancelDialog() }
    }
}
