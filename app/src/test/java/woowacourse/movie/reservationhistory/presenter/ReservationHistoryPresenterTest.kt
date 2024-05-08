package woowacourse.movie.reservationhistory.presenter

import android.content.Context
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.db.ReservationHistoryDAO
import woowacourse.movie.data.db.ReservationHistoryDatabase
import woowacourse.movie.data.db.ReservationHistoryEntity

class ReservationHistoryPresenterTest {
    private lateinit var view: ReservationHistoryContract.View
    private lateinit var presenter: ReservationHistoryPresenter
    private lateinit var database: ReservationHistoryDatabase
    private lateinit var mockContext: Context

    @BeforeEach
    fun setUp() {
        view = mockk()
        database = mockk()
        mockContext = mockk()
        presenter = ReservationHistoryPresenter(view, database)
    }

    @Test
    fun `loadReservationHistories를 호출하면 예매내역을 보여준다`() {
        // Given
        val mockReservationHistoryDao = mockk<ReservationHistoryDAO>()
        val mockEntityList = listOf(ReservationHistoryEntity("2024-03-17", "15:00", 1, "A1", 0L, 0))

        every { mockReservationHistoryDao.findReservationHistories() } returns mockEntityList
        every { database.reservationHistoryDao() } returns mockReservationHistoryDao
        every { view.displayReservationHistories(any()) } just Runs

        // When
        presenter.loadReservationHistories()

        // Then
        verify { view.displayReservationHistories(mockEntityList) }
    }
}
