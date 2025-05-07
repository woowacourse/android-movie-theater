package woowacourse.movie.presenter

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.booking.AdmissionCount
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.view.home.booking.BookingContract
import woowacourse.movie.view.home.booking.BookingPresenter
import woowacourse.movie.view.home.model.ScreeningInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenterTest {
    private val view: BookingContract.View = mockk<BookingContract.View>(relaxed = true)
    private lateinit var presenter: BookingPresenter

    @BeforeEach
    fun setUp() {
        presenter =
            BookingPresenter(
                view,
                ScreeningInfo(
                    movieId = 0,
                    theaterName = "CGV",
                    screenings = listOf(LocalDateTime.of(2025, 4, 10, 12, 10)),
                ),
            )
        presenter.initBooking(LocalDateTime.of(2025, 4, 10, 12, 0))
    }

    @Test
    fun `영화 정보를 UI에 표시한다`() {
        // then
        verify { view.showMovieDetail(any(), any()) }
    }

    @Test
    fun `인원은 0명이 될 수 없다`() {
        // given
        every { view.showAdmissionCount(1) } just Runs

        // when
        presenter.decreaseAdmissionCount()

        // then
        verify { view.showAdmissionCount(1) }
    }

    @Test
    fun `인원이 1명 증가한다`() {
        // given
        every { view.showAdmissionCount(1) }

        // when
        presenter.increaseAdmissionCount(2)

        // then
        verify { view.showAdmissionCount(2) }
    }

    @Test
    fun `인원이 1명 감소한다`() {
        // when
        presenter.booking = presenter.booking.copy(count = AdmissionCount(5))
        presenter.decreaseAdmissionCount()

        // then
        verify { view.showAdmissionCount(4) }
    }

    @Test
    fun `예약 버튼을 누르면 예약 완료 화면으로 이동하며 예약 정보 Booking 객체를 전달한다`() {
        // given
        presenter.booking =
            Booking(
                movieTitle = "테스트 영화 1",
                theaterName = "테스트 극장 1",
                screeningDate = LocalDate.of(2025, 4, 24),
                screeningTime = LocalTime.of(12, 0),
                count = AdmissionCount(3),
            )

        // when
        presenter.completeBooking()

        // then
        verify {
            view.moveToBookingComplete(
                match {
                    it.movieTitle == "테스트 영화 1" &&
                        it.theaterName == "테스트 극장 1" &&
                        it.screeningDate == LocalDate.of(2025, 4, 24) &&
                        it.screeningTime == LocalTime.of(12, 0) &&
                        it.count == AdmissionCount(3)
                },
            )
        }
    }
}
