package woowacourse.movie.presenter

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.MovieStore
import woowacourse.movie.domain.fixture.harryPotter1MoviesFixture
import woowacourse.movie.domain.model.booking.PeopleCount
import woowacourse.movie.view.home.booking.BookingContract
import woowacourse.movie.view.home.booking.BookingPresenter
import woowacourse.movie.view.home.model.ScreeningInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenterTest {
    private val view: BookingContract.View = mockk<BookingContract.View>(relaxed = true)
    private lateinit var model: MovieStore
    private lateinit var presenter: BookingPresenter

    @BeforeEach
    fun setUp() {
        model = MovieStore()
        presenter =
            BookingPresenter(
                view,
                MovieStore(),
                PeopleCount(1),
                ScreeningInfo(
                    movieId = 0,
                    theaterName = "CGV",
                    screening = listOf(LocalDateTime.of(2025, 4, 10, 12, 10)),
                ),
            )
    }

    @Test
    fun `영화 정보를 UI에 표시한다`() {
        // when
        presenter.loadMovieDetail()

        val expected = harryPotter1MoviesFixture

        // then
        verify {
            view.showMovieDetail(
                match {
                    it.id == expected.id &&
                        it.title == expected.title &&
                        it.posterResource == expected.posterResource &&
                        it.releaseDate == expected.releaseDate &&
                        it.runningTime == expected.runningTime
                },
                any(),
            )
        }
    }

    @Test
    fun `인원은 0명이 될 수 없다`() {
        // given
        var count = 1
        every { view.showPeopleCount(count) } just Runs

        // when
        presenter.decreasePeopleCount()

        // then
        verify { view.showPeopleCount(1) }
    }

    @Test
    fun `인원이 1명 증가한다`() {
        // given
        var count = 1
        every { view.showPeopleCount(count) }

        // when
        presenter.increasePeopleCount(2)

        // then
        verify { view.showPeopleCount(2) }
    }

    @Test
    fun `인원이 1명 감소한다`() {
        // given
        val presenter =
            BookingPresenter(
                view,
                model,
                PeopleCount(5),
                ScreeningInfo(
                    movieId = 0,
                    theaterName = "CGV",
                    screening = listOf(LocalDateTime.of(2025, 4, 10, 12, 10)),
                ),
            )
        every { view.showPeopleCount(4) } just Runs

        // when
        presenter.decreasePeopleCount()

        // then
        verify { view.showPeopleCount(4) }
    }

    @Test
    fun `예약 버튼을 누르면 예약 완료 화면으로 이동하며 예약 정보 Booking 객체를 전달한다`() {
        // when
        presenter.loadBooking(
            title = "테스트 영화 1",
            bookingDate = "2025-04-24",
            bookingTime = "12:00",
            count = "3",
        )

        // then
        verify {
            view.moveToBookingComplete(
                match {
                    it.title == "테스트 영화 1" &&
                        it.bookingDate == LocalDate.of(2025, 4, 24) &&
                        it.bookingTime == LocalTime.of(12, 0) &&
                        it.count == PeopleCount(3)
                },
            )
        }
    }
}
