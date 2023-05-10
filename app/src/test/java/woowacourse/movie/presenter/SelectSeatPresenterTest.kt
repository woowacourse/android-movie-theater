package woowacourse.movie.presenter

import domain.Reservation
import io.mockk.* // ktlint-disable no-wildcard-imports
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.database.ReservationDbHelperInterface
import woowacourse.movie.model.* // ktlint-disable no-wildcard-imports
import woowacourse.movie.view.seatselect.SelectSeatContract
import woowacourse.movie.view.seatselect.SelectSeatPresenter
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
            LocalDateTime.of(2023, 5, 6, 11, 0),
        )
        presenter = SelectSeatPresenter(
            view,
            ticketOfficeUiModel,
            movieUiModel,
            reservationDb,
        )
    }

    @Test
    fun 좌석의_랭크를_업데이트한후_좌석_글자색을_초기화한다() {
        // given
        val seatUiModels = List(20) { SeatUiModel('A', it) }
        val slot = slot<List<SeatRankUiModel>>()
        every { view.setSeatsTextColor(capture(slot)) } just runs
        // when
        presenter.updateSeatsRank(seatUiModels = seatUiModels)
        // then
        verify { view.setSeatsTextColor(slot.captured) }
    }

    @Test
    fun 좌석을_누르면_티켓을_업데이트하고_좌석의상태를_바꾼다() {
        // given
        val seatUiModel = SeatUiModel('A', 1)
        val slotTicketsUiModel = slot<TicketsUiModel>()
        every { view.setSeatsBackgroundColor(capture(slotTicketsUiModel)) } just runs
        // when
        presenter.updateTickets(seatUiModel)
        // then
        verify { view.setSeatsBackgroundColor(slotTicketsUiModel.captured) }
    }

    @Test
    fun 좌석을_누르면_가격을_계산한후_text에표시한다() {
        // given
        val slot = slot<Int>()
        every { view.setPriceText(capture(slot)) } just runs
        // when
        presenter.calculatePrice()
        // then
        assertEquals(slot.captured, 0)
        verify { view.setPriceText(slot.captured) }
    }

    @Test
    fun 좌석을_누르면_버튼의_상태를_바꾼다() {
        // given
        val slotClickable = slot<Boolean>()
        val slotColorCondition = slot<Boolean>()
        every { view.setCheckButtonClickable(capture(slotClickable)) } just runs
        every { view.setCheckButtonColorBy(capture(slotColorCondition)) } just runs
        // when
        presenter.changeButtonState()
        // then
        assertEquals(slotClickable.captured, false)
        verify { view.setCheckButtonClickable(slotClickable.captured) }
        assertEquals(slotColorCondition.captured, false)
        verify { view.setCheckButtonColorBy(slotColorCondition.captured) }
    }

    @Test
    fun 확인_버튼을_누르면_다이얼로그가_나온다() {
        // given
        every { view.askConfirmReservation() } just runs
        // when
        presenter.completeReservation()
        // then
        verify { view.askConfirmReservation() }
    }

    @Test
    fun 다이얼로그_버튼을_누르면_알람을_등록한다() {
        // given
        val slotTicket = slot<TicketsUiModel>()
        val slotMovie = slot<MovieUiModel>()
        every { view.setAlarm(capture(slotMovie), capture(slotTicket)) } just runs
        // when
        presenter.registerAlarm()
        // then
        verify { view.setAlarm(slotMovie.captured, slotTicket.captured) }
    }

    @Test
    fun 다이얼로그_버튼을_누르면_예약결과_화면을_보여준다() {
        // given
        val slotTicket = slot<TicketsUiModel>()
        val slotMovie = slot<MovieUiModel>()
        every { view.showResultScreen(capture(slotMovie), capture(slotTicket)) } just runs
        // when
        presenter.showResult()
        // then
        verify { view.showResultScreen(slotMovie.captured, slotTicket.captured) }
    }

    @Test
    fun 다이얼로그_버튼을_누르면_예약정보를_저장한다() {
        // given
        val slot = slot<Reservation>()
        every { reservationDb.saveReservation(capture(slot)) } just runs
        // when
        presenter.saveReservation()
        // then
        verify { reservationDb.saveReservation(slot.captured) }
    }
}
