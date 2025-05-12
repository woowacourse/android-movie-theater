package woowacourse.movie.presentation.history

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.ReservationRepository

class ReservationHistoryPresenterTest {
    private lateinit var view: ReservationHistoryContract.View
    private lateinit var presenter: ReservationHistoryContract.Presenter
    private lateinit var repository: ReservationRepository

    @BeforeEach
    fun setUp() {
        view = mockk()
        repository = mockk()
        presenter = ReservationHistoryPresenter(view, repository)
    }

    @Test
    fun `예매 내역을 불러와 화면에 보여준다`() {
        // Give
        every { view.showReservationHistory(any()) } just Runs
        every { repository.getAll() } returns emptyList()

        // When
        presenter.fetchData()

        // Then
        verify { view.showReservationHistory(any()) }
    }
}
