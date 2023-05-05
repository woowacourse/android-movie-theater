package woowacourse.movie.presentation.views.main.fragments.history.contract.presenter

import com.woowacourse.data.repository.history.HistoryRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.views.main.fragments.history.contract.HistoryContract
import woowacourse.movie.presentation.mapper.toDomain
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketPrice
import woowacourse.movie.presentation.model.movieitem.ListItem

internal class HistoryPresenterTest {

    private lateinit var presenter: HistoryContract.Presenter
    private lateinit var historyRepository: HistoryRepository
    private lateinit var view: HistoryContract.View
    private val reservation: Reservation = Reservation(
        "title",
        MovieDate(2023, 3, 3),
        MovieTime(5, 5),
        Ticket(5),
        PickedSeats(),
        TicketPrice(1000)
    )
    private val storedDataSize = 5

    @Before
    fun setUp() {
        historyRepository = mockk {
            every { getAll() } returns List(storedDataSize) { reservation.toDomain() }
        }
        view = mockk(relaxed = true)
        presenter = HistoryPresenter(historyRepository)
        presenter.attach(view)
    }

    @Test
    internal fun 프레젠터에_화면을_Attach하면_예매_내역_데이터를_불러온다() {
        // given
        val slot = slot<List<ListItem>>()
        every { view.showHistories(capture(slot)) } answers { nothing }

        // when
        presenter.attach(view)

        // then
        assertEquals(slot.captured.size, storedDataSize)
    }

    @Test
    internal fun 예매_내역을_추가하면_리스트에도_새로운_내역을_추가한다() {
        // given
        /* ... */

        // when
        presenter.addHistory(reservation)

        // then
        verify(exactly = 1) { view.showMoreHistory(any()) }
    }

    @Test
    internal fun 영화_목록을_불러오면_화면을_갱신한다() {
        // given
        /* ... */

        // when
        presenter.loadHistories()

        // then
        verify(exactly = 2) { view.showHistories(any()) }
    }

    @Test
    internal fun 예매_내역을_클릭하면_상세_내역을_보여준다() {
        // given
        /* ... */

        // when
        presenter.onClickItem(reservation)

        // then
        verify(exactly = 1) { view.showDetails(any()) }
    }
}
