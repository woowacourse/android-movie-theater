package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.dummy.TheaterStore
import woowacourse.movie.domain.model.booking.AdmissionCount
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.theater.Screening
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.view.home.model.ScreeningInfo
import woowacourse.movie.view.home.theaters.TheaterListContract
import woowacourse.movie.view.home.theaters.TheaterListPresenter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TheaterListPresenterTest {
    private lateinit var view: TheaterListContract.View
    private lateinit var theaterStore: TheaterStore
    private lateinit var presenter: TheaterListPresenter
    private val booking =
        Booking(
            movieTitle = "해리 포터와 마법사의 돌",
            screeningDate = LocalDate.now(),
            screeningTime = LocalTime.now(),
            count = AdmissionCount(3),
            theaterName = "선릉 극장",
        )

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        theaterStore = mockk(relaxed = true)
        presenter = TheaterListPresenter(view, theaterStore)
    }

    @Test
    fun `극장 클릭 시 예매 화면으로 이동한다`() {
        // given
        val theater =
            Theater(
                "선릉 극장",
                listOf(Screening(0, LocalDateTime.of(2025, 5, 11, 12, 0))),
            )
        val screeningInfo =
            ScreeningInfo(
                0,
                "선릉 극장",
                listOf(LocalDateTime.of(2025, 5, 11, 12, 0)),
            )

        // when
        presenter.selectTheater(0, theater)

        // then
        verify { view.moveToBooking(screeningInfo) }
    }
}
