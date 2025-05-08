package woowacourse.movie.presentation.history

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.ReservationDaoListener

class ReservationHistoryPresenterTest {
    private lateinit var view: ReservationHistoryContract.View
    private lateinit var presenter: ReservationHistoryContract.Presenter
    private lateinit var daoListener: ReservationDaoListener

    @BeforeEach
    fun setUp() {
        view = mockk()
        daoListener = mockk()
        presenter = ReservationHistoryPresenter(view, daoListener)
    }

    @Test
    fun `예매 내역을 불러와 화면에 보여준다`() {
        // Give
        every { view.showReservationHistory(any()) } just Runs
        every { daoListener.getAll() } returns emptyList()

        // When
        presenter.fetchData()

        // Then
        verify { view.showReservationHistory(any()) }
    }
}
