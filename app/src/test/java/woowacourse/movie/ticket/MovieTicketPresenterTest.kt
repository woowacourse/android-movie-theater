package woowacourse.movie.ticket

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.model.TicketDataResource
import woowacourse.movie.ticket.presenter.MovieTicketPresenter

class MovieTicketPresenterTest {
    private lateinit var view: MovieTicketContract.View
    private lateinit var presenter: MovieTicketContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk<MovieTicketContract.View>()
        presenter = MovieTicketPresenter(view)
    }

    @Test
    fun `티켓 정보를 db에 저장할 수 있어야 한다`() {
        every { view.storeTicketInDb(any()) } just runs
        // when
        presenter.storeTicketInDb()
        // then
        verify { view.storeTicketInDb(TicketDataResource.dbTicket) }
    }

    @Test
    fun `티켓을 뷰에 띄울 수 있어야 한다`() {
        every { view.showTicketView(any()) } just runs
        // when
        presenter.setTicketInfo()
        // then
        verify { view.showTicketView(TicketDataResource.dbTicket) }
    }
}
