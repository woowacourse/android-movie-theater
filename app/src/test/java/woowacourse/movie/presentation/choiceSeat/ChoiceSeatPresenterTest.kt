package woowacourse.movie.presentation.choiceSeat

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.settings.SettingsData
import woowacourse.movie.presentation.FakeMovieData
import woowacourse.movie.presentation.MockBookedTicketsData
import woowacourse.movie.presentation.model.ReservationModel
import woowacourse.movie.presentation.model.TicketModel
import java.time.LocalDateTime

class ChoiceSeatPresenterTest {
    private lateinit var view: ChoiceSeatContract.View
    private lateinit var presenter: ChoiceSeatContract.Presenter
    private lateinit var reservationModel: ReservationModel
    private lateinit var settingsData: SettingsData

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        settingsData = mockk(relaxed = true)
        // FakeMovieData 사용
        presenter =
            ChoiceSeatPresenter(view, FakeMovieData, settingsData, MockBookedTicketsData)
        reservationModel = ReservationModel(
            1L,
            "선릉",
            LocalDateTime.of(2024, 3, 1, 9, 0),
            2,
        )
    }

    @Test
    fun `MovieId 가 1이면 해리 포터 영화 제목을 세팅 한다`() {
        // given
        val movieTitleSlot = slot<String>()
        every { view.setMovieTitle(capture(movieTitleSlot)) } just runs

        // when
        presenter.setMovieTitle(1L)

        val expected = "해리 포터와 마법사의 돌"
        val actual = movieTitleSlot.captured

        TestCase.assertEquals(expected, actual)
        verify { view.setMovieTitle(expected) }
    }

    @Test
    fun `좌석을 더하면 지불 금액은 0이 아니다`() {
        val paymentAmountSlot = slot<Int>()
        every { view.setPaymentAmount(capture(paymentAmountSlot)) } just runs

        // when
        presenter.addSeat(5, reservationModel)

        // then
        val expected = 0
        val actual = paymentAmountSlot.captured

        assertNotEquals(expected, actual)
    }

    @Test
    fun `같은 좌석을 더했다가 빼면 지불 금액은 0이 된다`() {
        val paymentAmountSlot = slot<Int>()
        every { view.setPaymentAmount(capture(paymentAmountSlot)) } just runs

        // when
        presenter.addSeat(5, reservationModel)
        presenter.subSeat(5, reservationModel)

        // then
        val expected = 0
        val actual = paymentAmountSlot.captured

        assertEquals(expected, actual)
        verify { view.setPaymentAmount(expected) }
    }

    @Test
    fun `영화 티켓을 발급한다`() {
        val ticketModelSlot = slot<TicketModel>()
        every { view.confirmBookMovie(capture(ticketModelSlot)) } just runs

        // when
        presenter.addSeat(1, reservationModel)
        presenter.addSeat(2, reservationModel)
        presenter.reserveTicketModel(reservationModel)

        val expected = TicketModel(
            1L,
            "선릉",
            LocalDateTime.of(2024, 3, 1, 9, 0),
            2,
            16000,
            listOf("A2", "A3"),
        )
        val actual = ticketModelSlot.captured

        assertEquals(expected, actual)
        verify { view.confirmBookMovie(expected) }
    }
}
