package woowacourse.movie.presentation.history

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.ReservationRepository
import woowacourse.movie.domain.model.reservation.ReservationHistory

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
    fun `예매_내역을_불러와_화면에_보여준다`() {
        // Given
        every { view.showReservationHistory(any()) } just Runs
        every { repository.getAll(any()) } answers {
            arg<(List<ReservationHistory>) -> Unit>(0).invoke(listOf(ReservationHistory(1, mockk(relaxed = true))))
        }

        // When
        presenter.fetchData()

        // Then
        verify { view.showReservationHistory(any()) }
    }
}
