package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.ui.complete.BookingCompleteContract
import woowacourse.movie.ui.complete.BookingCompletePresenter
import woowacourse.movie.utils.Destination
import java.time.LocalDateTime

class BookingCompletePresenterTest {
    private lateinit var view: BookingCompleteContract.View
    private lateinit var presenter: BookingCompletePresenter

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = BookingCompletePresenter(view)
        presenter.loadBookedTicket(
            bookedTicket =
                BookedTicket(
                    theaterName = "선릉 극장",
                    movieTitle = "해리 포터",
                    movieSchedule =
                        MovieSchedule(
                            LocalDateTime.of(2025, 1, 1, 12, 0),
                            Seats().apply { reserve(Seat(1, 1)) },
                        ),
                    headcount = Headcount(1),
                ),
            Destination.COMPLETE,
        )
    }

    @Test
    fun `티켓의 가격이 뷰에 반영된다`() {
        verify { view.showTotalPrice(any()) }
    }

    @Test
    fun `티켓의 정보가 업데이트되면 뷰에 반영된다`() {
        verify {
            view.showMovieTitle(any())
            view.showScreeningDateTime(any())
            view.showDetailInfos(any(), any(), any())
        }
    }
}
