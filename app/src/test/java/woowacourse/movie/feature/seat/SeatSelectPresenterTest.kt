package woowacourse.movie.feature.seat

import com.example.domain.model.TicketOffice
import com.example.domain.repository.TicketsRepository
import io.mockk.checkUnnecessaryStub
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SelectReservationState
import woowacourse.movie.model.TheaterState
import woowacourse.movie.model.TicketState
import woowacourse.movie.model.TicketsState
import java.time.LocalDate
import java.time.LocalDateTime

@MockKExtension.CheckUnnecessaryStub
internal class SeatSelectPresenterTest {

    private val view: SeatSelectContract.View = mockk()
    private val ticketsRepository: TicketsRepository = mockk()
//    private val ticketOffice: TicketOffice = mockk()

    private val theaterState: TheaterState = TheaterState(theaterId = 0, theaterName = "")
    private val movieState: MovieState = MovieState(
        id = 0, imgId = 0, title = "테스트 영화",
        startDate = LocalDate.of(2023, 4, 1),
        endDate = LocalDate.of(2023, 5, 31),
        runningTime = 120, description = ""
    )
    private val reserveDateTime: LocalDateTime = LocalDateTime.of(2023, 5, 28, 15, 32)
    private val ticketState: TicketState = TicketState(
        theater = theaterState, movie = movieState, dateTime = reserveDateTime,
        seatPositionState = SeatPositionState(row = 1, column = 1),
        discountedMoneyState = MoneyState(price = 0)
    )
    private val ticketsState: TicketsState = TicketsState(
        theater = theaterState, movie = movieState, dateTime = reserveDateTime,
        totalDiscountedMoneyState = MoneyState(price = 10_000), tickets = listOf(ticketState)
    )
    private val reservationState: SelectReservationState = SelectReservationState(
        theater = theaterState, movie = movieState, selectDateTime = reserveDateTime,
        selectCount = 1
    )

    // system under test
    private lateinit var sut: SeatSelectContract.Presenter

    @Before
    fun set() {
//        every { ticketOffice.predictMoney(any(), any(), any()) } just awaits
        every { view.setViewContents(reservationState) } just runs
        every { view.changePredictMoney(any()) } just runs // saved data를 불러올 때 (init) 호출
        every { view.setConfirmClickable(any()) } just runs // saved data를 불러올 때 (init) 호출

        sut = SeatSelectPresenter(
            view = view, reservationState = reservationState,
            ticketOffice = TicketOffice(),
            ticketsRepository = ticketsRepository
        )
    }

    @After
    fun check() {
        checkUnnecessaryStub(view)
    }

    @Test
    fun `선택되지 않은 좌석을 클릭하면 선택 상태로 바꾸고, 금액을 업데이트한다`() {
        // given
        every { view.changePredictMoney(any()) } just runs
        every { view.setConfirmClickable(any()) } just runs
        every { view.changeSeatCheckedByIndex(any()) } just runs

        // when
        sut.checkSeat(0)

        // then
        verify { view.changeSeatCheckedByIndex(0) }
        verify { view.changePredictMoney(ticketsState.totalDiscountedMoneyState) }
    }

    @Test
    fun `선택되지 않은 좌석을 클릭했을 때 선택한 티켓 수만큼 좌석을 선택한 상태라면 예약 버튼을 활성화한다`() {
        // given
        every { view.changeSeatCheckedByIndex(any()) } just runs

        // when
        sut.checkSeat(0)

        // then
        verify { view.changeSeatCheckedByIndex(0) }
        verify { view.changePredictMoney(ticketsState.totalDiscountedMoneyState) }
        verify { view.setConfirmClickable(true) }
    }

    @Test
    fun `예약 의사를 재확인하는 다이얼로그를 띄운다`() {
        // given
        every { view.showReservationConfirmationDialog() } just runs

        // when
        sut.showReservationConfirmationDialog()

        // then
        verify { view.showReservationConfirmationDialog() }
    }

//    @Test
//    fun `예약을 저장 하고, 영화 시작 전 알림을 설정하고, 예약 확인 화면을 띄운다`() {
//        // given
//        every { view.changeSeatCheckedByIndex(any()) } just runs
//        sut.checkSeat(0)
//
//        every { ticketsRepository.addTicket(any()) } just runs
//        every { view.setReservationAlarm(any()) } just runs
//        every { view.navigateReservationConfirm(any()) } just runs
//
//        // when
//        sut.reserveTickets()
//
//        // then
//        verify { ticketsRepository.addTicket(ticketsState.asDomain()) }
//        verify { view.setReservationAlarm(ticketsState) }
//        verify { view.navigateReservationConfirm(ticketsState) }
//    }
}
