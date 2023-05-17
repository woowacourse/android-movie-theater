package woowacourse.movie.presentation.view.main.home.seatpick

import com.example.domain.TicketBundle
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.SharedPreference
import woowacourse.movie.data.database.MovieHelper
import woowacourse.movie.data.model.MovieBookingEntity
import woowacourse.movie.presentation.model.Movie
import woowacourse.movie.presentation.model.MovieBookingInfo
import woowacourse.movie.presentation.model.ReservationResult


internal class SeatPickerPresenterTest {
    lateinit var presenter: SeatPickerPresenter
    lateinit var view: SeatPickerContract.View
    lateinit var sharedPreferenceUtil: SharedPreference
    lateinit var movieHelper: MovieHelper
    lateinit var ticketBundle: TicketBundle

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        sharedPreferenceUtil = mockk(relaxed = true)
        movieHelper = mockk(relaxed = true)
        presenter = SeatPickerPresenter(
            view,
            sharedPreferenceUtil,
            movieHelper,
            MovieBookingInfo(
                Movie(
                    0,
                    "타이틀",
                    listOf("13:00"),
                    "2020.08.05",
                    "130",
                    "",
                    mockk(),
                    mockk()

                ),
                "2021-08-01",
                "13:00",
                1,
                "극장"
            )

        )

        ticketBundle = mockk()
    }

    @Test
    fun `티켓의_총_가격합은_0으로_초기화_된다`() {
        val totalPrice = 0

        val slot = slot<String>()
        justRun { view.updateDefaultTotalPriceView(capture(slot)) }
        presenter.getDefaultTotalPrice()

        assertEquals(totalPrice.toString(), slot.captured)
    }

    @Test
    fun `예매_성공시_데이터베이스에_추가한다`() {
        val movieBookingEntity = MovieBookingEntity(
            "타이틀",
            "2021-08-01",
            "13:00",
            0,
            "",
            0,
        )
        every { movieHelper.writeMovie(movieBookingEntity) } just Runs
        justRun { view.updateNotification(ReservationResult.from(movieBookingEntity), 1) }
        presenter.bookComplete()

        verify { movieHelper.writeMovie(movieBookingEntity) }
    }
}