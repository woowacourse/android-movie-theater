package woowacourse.movie.feature.seatSelect

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.ReservationState
import woowacourse.movie.model.SeatPositionState
import java.time.LocalDate
import java.time.LocalDateTime

internal class SeatSelectPresenterTest {
    private lateinit var view: SeatSelectContract.View
    private lateinit var presenter: SeatSelectContract.Presenter
    private lateinit var ticketsRepository: TicketsRepository

    private var moneySlot = slot<MoneyState>()
    private var clickableSlot = slot<Boolean>()

    @Before
    fun init() {
        moneySlot = slot<MoneyState>()
        clickableSlot = slot<Boolean>()

        view = mockk()
        every { view.changePredictMoney(capture(moneySlot)) } just Runs
        every { view.setConfirmClickable(capture(clickableSlot)) } just Runs
        ticketsRepository = mockk()
        presenter = SeatSelectPresenter(view, mockkReservation, ticketsRepository)
    }

    @Test
    fun 좌석을_선택개수에_모자르게_클릭하면_해당좌석이_선택되고_예상금액이_업데이트되고_확인버튼의_활성화버튼이_아직_비활성화로_유지된다() {
        val indexSlot = slot<Int>()
        every { view.seatToggle(capture(indexSlot)) } just Runs

        // 실행
        presenter.clickSeat(1)

        // 좌석이 선택된다
        assert(SeatPositionState(1, 2) in presenter.seats)
        val actual = indexSlot.captured
        val expected = 1
        assert(actual == expected)
        verify { view.seatToggle(1) }
        // 예상 금액 업데이트 된다
        val actualMoney = moneySlot.captured
        val expectedMoney = MoneyState(10000)
        assert(actualMoney.price == expectedMoney.price)
        verify { view.changePredictMoney(expectedMoney) }
        // 확인 버튼이 아직 비활성화 상태다
        val actualConfirmClickable = clickableSlot.captured
        val expectedConfirmClickable = false
        assert(actualConfirmClickable == expectedConfirmClickable)
        verify { view.setConfirmClickable(expectedConfirmClickable) }
    }

    @Test
    fun 좌석을_선택개수에_맞게_클릭하면_해당좌석들이_선택되고_예상금액이_업데이트되고_확인버튼의_활성화버튼이_활성화된다() {
        val indexSlot = slot<Int>()
        every { view.seatToggle(capture(indexSlot)) } just Runs

        // 실행
        presenter.clickSeat(1)
        presenter.clickSeat(2)

        // 좌석이 선택된다
        assert(SeatPositionState(1, 2) in presenter.seats)
        assert(SeatPositionState(1, 3) in presenter.seats)
        verify(exactly = 2) { view.seatToggle(any()) }
        // 예상 금액 업데이트 된다
        val actualMoney = moneySlot.captured
        val expectedMoney = MoneyState(20000)
        assert(actualMoney.price == expectedMoney.price)
        verify { view.changePredictMoney(expectedMoney) }
        // 확인 버튼이 아직 비활성화 상태다
        val actualConfirmClickable = clickableSlot.captured
        val expectedConfirmClickable = true
        assert(actualConfirmClickable == expectedConfirmClickable)
        verify { view.setConfirmClickable(expectedConfirmClickable) }
    }

    @Test
    fun 예매_확인_버튼을_클릭하면_다이얼로그가_나온다() {
        every { view.showDialog() } just Runs

        presenter.clickConfirm()
        verify { view.showDialog() }
    }

    @Test
    fun 다이얼로그_확인_버튼을_클릭하면_예매_확인_화면으로_넘어간다() {
        every { view.seatToggle(any()) } just Runs
        every { view.setReservationAlarm(any(), any(), any()) } just Runs
        every { ticketsRepository.addTicket(any()) } just Runs
        every { view.navigateReservationConfirm(any()) } just Runs

        // 좌석선택
        presenter.clickSeat(1)
        presenter.clickSeat(2)
        // 선택된 좌석들로 티켓을 발급받고 다음 화면으로 넘어가기 위해 다이얼로그 버튼을 클릭
        presenter.clickDialogConfirm()

        verify { view.setReservationAlarm(any(), any(), any()) }
        verify { ticketsRepository.addTicket(any()) }
        verify { view.navigateReservationConfirm(any()) }
    }

    private val mockkReservation = ReservationState(
        "선릉 극장",
        MovieState(
            R.drawable.imitation_game_poster,
            "이미테이션 게임",
            LocalDate.of(2023, 5, 1),
            LocalDate.of(2023, 8, 20),
            114,
            "매 순간 3명이 죽는 사상 최악의 위기에 처한 제 2차 세계대전. 절대 해독이 불가능한 암호 ‘에니그마’로 인해 연합군은 속수무책으로 당하게 된다. 결국 각 분야의 수재들을 모아 기밀 프로젝트 암호 해독팀을 가동한다. 천재 수학자 앨런 튜링(베네딕트 컴버배치)은 암호 해독을 위한 특별한 기계를 발명하지만 24시간 마다 바뀌는 완벽한 암호 체계 때문에 번번히 좌절하고 마는데... 과연, 앨런 튜링과 암호 해독팀은 암호를 풀고 전쟁의 승리를 끌어낼 수 있을까…?"
        ),
        LocalDateTime.of(2023, 5, 26, 14, 0),
        CountState.of(2)
    )
}
