package woowacourse.movie.presenter.list

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.repository.Repository
import woowacourse.movie.view.reservelist.ReservationListContract
import woowacourse.movie.view.reservelist.ReservationListPresenter

class ReservationListPresenterTest {
    private lateinit var presenter: ReservationListPresenter
    private lateinit var view: ReservationListContract.View
    private lateinit var repository: Repository<Ticket>

    @BeforeEach
    fun setUp() {
        view = mockk()
        repository = mockk()
        presenter = ReservationListPresenter(view, repository)
    }

    @Test
    fun `데이터베이스에서 저장된 예매 목록을 불러올 수 있다`() {
        // given
        val ticket: Ticket = mockk()
        every { repository.findAll() } returns Result.success(listOf(ticket))
        every { view.showReservationList(any()) } just Runs

        // when
        presenter.loadData()

        // then
        verify { repository.findAll() }
        verify { view.showReservationList(listOf(ticket)) }
    }
}
