package woowacourse.movie.presenter.reservationComplete

import io.kotest.core.spec.style.AnnotationSpec.After
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.invoke
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.data.storage.ReservationStorage
import woowacourse.movie.presenter.MOVIE_TICKET_ENTITY

class ReservationCompletePresenterTest {
    private lateinit var presenter: ReservationCompletePresenter
    private lateinit var view: ReservationCompleteContracts.View
    private lateinit var reservationStorage: ReservationStorage

    @BeforeEach
    fun setup() {
        view = mockk()
        reservationStorage = mockk()
        presenter = ReservationCompletePresenter(view, reservationStorage)
    }

    @Test
    fun `영화 티켓을 업데이트 하면 영화 티켓에 대한 정보가 보인다`() {
        // given:
        every { view.showMovieTicket(any()) } just Runs
        every { reservationStorage.getMovieTicket(any(), captureLambda()) } answers {
            lambda<(MovieTicketEntity) -> Unit>().invoke(MOVIE_TICKET_ENTITY)
        }

        // when:
        presenter.updateTicketData(1L)

        // then:
        verify { view.showMovieTicket(MOVIE_TICKET_ENTITY) }
    }

    @After
    fun finish() {
        clearAllMocks()
    }
}
