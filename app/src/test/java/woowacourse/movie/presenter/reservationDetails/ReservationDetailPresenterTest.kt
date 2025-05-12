package woowacourse.movie.presenter.reservationDetails

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReservationDetailPresenterTest {
    private lateinit var view: ReservationDetailContracts.View
    private lateinit var presenter: ReservationDetailPresenter
    private lateinit var dao: ReservationDao

    @BeforeEach
    fun setup() {
        view = mockk()
        dao = mockk<ReservationDao>()
        presenter = ReservationDetailPresenter(view, dao) { it.run() }
    }

    @Test
    fun `예매 내역의 데이터를 화면에 보인다`() {
        // given
        every { dao.findReservations() } returns emptyList()
        every { view.showReservations(any()) } just Runs

        // when
        presenter.loadReservations()

        // then
        verify {
            dao.findReservations()
            view.showReservations(any())
        }
    }

    @Test
    fun `특정 예매 내역을 클릭하면 해당 예매완료 화면으로 이동한다`() {
        // given
        every { dao.findReservation(any()) } returns mockk()
        every { view.showReservationCompleteView(any()) } just Runs

        // when
        presenter.requestReservationComplete(1L)

        // then
        verify {
            dao.findReservation(any())
            view.showReservationCompleteView(any())
        }
    }
}
