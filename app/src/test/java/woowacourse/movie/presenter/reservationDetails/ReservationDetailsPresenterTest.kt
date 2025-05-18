package woowacourse.movie.presenter.reservationDetails

import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.invoke
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.data.storage.ReservationStorage
import woowacourse.movie.presenter.MOVIE_TICKET_ENTITY
import woowacourse.movie.presenter.reservationDetail.ReservationDetailsContracts
import woowacourse.movie.presenter.reservationDetail.ReservationDetailsPresenter

class ReservationDetailsPresenterTest {
    private lateinit var reservationDetailsPresenter: ReservationDetailsPresenter
    private lateinit var view: ReservationDetailsContracts.View
    private lateinit var reservationStorage: ReservationStorage

    @BeforeEach
    fun setup() {
        view = mockk()
        reservationStorage = mockk()
        reservationDetailsPresenter = ReservationDetailsPresenter(view, reservationStorage)
    }

    @Test
    fun `예매 내역들을 업데이트 하면 저장소의 모든 영화 티켓을 가져오고 예약 내역들을 보여준다`() {
        // given:
        every { reservationStorage.getAllMovieTickets(captureLambda()) } answers {
            lambda<(List<MovieTicketEntity>) -> Unit>().invoke(listOf(MOVIE_TICKET_ENTITY))
        }
        every { view.showReservationDetails(any()) } just Runs

        // when:
        reservationDetailsPresenter.fetchReservationDetails()

        // then:
        verify { view.showReservationDetails(any()) }
    }

    @AfterEach
    fun finish() {
        clearAllMocks()
    }
}
