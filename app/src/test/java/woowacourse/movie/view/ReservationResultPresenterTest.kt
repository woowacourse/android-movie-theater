package woowacourse.movie.view

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.activity.reservationresult.ReservationResultContract
import woowacourse.movie.activity.reservationresult.ReservationResultPresenter
import woowacourse.movie.view.data.PriceViewData
import woowacourse.movie.view.data.ReservationDetailViewData
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.SeatViewData
import java.time.LocalDateTime

class ReservationResultPresenterTest {
    private lateinit var presenter: ReservationResultContract.Presenter
    private lateinit var view: ReservationResultContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = ReservationResultPresenter(view)
    }

    @Test
    fun `예약 정보를 포맷팅해서 뷰에 넘겨준다`() {
        // given
        val reservationViewData: ReservationViewData = mockk()
        every { reservationViewData.reservationDetail } returns ReservationDetailViewData(
            LocalDateTime.of(2023, 5, 5, 10, 0), 1, "선릉"
        )
        every { reservationViewData.seats.seats } returns listOf(SeatViewData(0, 1, 1))
        every { reservationViewData.price } returns PriceViewData(10000)
        val capturedDate = slot<String>()
        val capturedPeopleCount = slot<String>()
        val capturedPrice = slot<String>()
        every {
            view.renderReservation(
                capture(capturedDate),
                capture(capturedPeopleCount),
                capture(capturedPrice)
            )
        } answers { nothing }

        // when
        presenter.renderReservation(reservationViewData)

        // then
        val actualDate = capturedDate.captured
        val actualPeopleCount = capturedPeopleCount.captured
        val actualPrice = capturedPrice.captured
        assertEquals("2023-05-05 10:00", actualDate)
        assertEquals("일반 1명 | A2 | 선릉 극장", actualPeopleCount)
        assertEquals("10,000원 (현장 결제)", actualPrice)
    }
}
