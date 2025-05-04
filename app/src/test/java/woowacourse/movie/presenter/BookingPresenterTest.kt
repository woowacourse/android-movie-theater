package woowacourse.movie.presenter

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.MovieStore
import woowacourse.movie.domain.fixture.harryPotter1MoviesFixture
import woowacourse.movie.domain.model.booking.PeopleCount
import woowacourse.movie.domain.model.movies.Movie
import woowacourse.movie.view.bindingadapter.ImageSource
import woowacourse.movie.view.home.booking.BookingContract
import woowacourse.movie.view.home.booking.BookingPresenter
import woowacourse.movie.view.home.movies.model.ScreeningInfo
import woowacourse.movie.view.uiModel.MovieUiModel
import java.time.LocalDate
import java.time.LocalDateTime

class BookingPresenterTest {
    private val view: BookingContract.View = mockk<BookingContract.View>(relaxed = true)
    private val movieStore = mockk<MovieStore>()
    private val initialTime = LocalDateTime.of(2025, 4, 20, 10, 0)
    private val movieFixture: Movie = harryPotter1MoviesFixture
    private val screeningDateTime = LocalDateTime.of(2025, 4, 24, 12, 0)

    private lateinit var presenter: BookingPresenter

    @BeforeEach
    fun setUp() {
        every { movieStore[0] } returns movieFixture

        presenter =
            BookingPresenter(
                view = view,
                movies = movieStore,
                screeningInfo =
                    ScreeningInfo(
                        movieId = 0,
                        theaterName = "CGV",
                        screening = listOf(screeningDateTime),
                    ),
                initialTime = initialTime,
            )
    }

    @Test
    fun `영화 정보를 UI에 표시한다`() {
        // when
        presenter.loadMovieDetail()

        val expected =
            MovieUiModel(
                title = "해리 포터와 마법사의 돌",
                posterResource = ImageSource.Resource(harryPotter1MoviesFixture.posterResource),
                screeningStartDate = "2025.5.1",
                screeningEndDate = "2025.5.4",
                runningTime = "152",
            )

        // then
        verify {
            view.showMovieDetail(
                match {
                    it.title == expected.title &&
                        it.posterResource == expected.posterResource &&
                        it.screeningStartDate == expected.screeningStartDate &&
                        it.screeningEndDate == expected.screeningEndDate &&
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
        every { view.showPeopleCount(1) }

        // when
        presenter.increasePeopleCount(2)

        // then
        verify { view.showPeopleCount(2) }
    }

    @Test
    fun `인원이 1명 감소한다`() {
        // given
        presenter.increasePeopleCount(limit = 5)
        presenter.increasePeopleCount(limit = 5)

        // when
        presenter.decreasePeopleCount()

        // then
        verify { view.showPeopleCount(2) }
    }

    @Test
    fun `예약 버튼을 누르면 예약 완료 화면으로 이동하며 예약 정보 Booking 객체를 전달한다`() {
        // given
        presenter.loadMovieDetail()
        presenter.restoreSavedData(0, 0, 3)
        presenter.loadScreeningTime(
            selectedDate = LocalDate.of(2025, 5, 4),
            now = LocalDateTime.of(2025, 5, 4, 12, 0),
        )

        // when
        presenter.loadBooking()

        val expected =
            MovieUiModel(
                title = "해리 포터와 마법사의 돌",
                posterResource = ImageSource.Resource(harryPotter1MoviesFixture.posterResource),
                screeningStartDate = "2025.5.1",
                screeningEndDate = "2025.5.4",
                runningTime = "152",
            )

        // then
        verify {
            view.moveToBookingComplete(
                match {
                    it.theaterName == "CGV" &&
                        it.movieTitle == expected.title &&
                        it.bookingDate == screeningDateTime.toLocalDate() &&
                        it.bookingTime == screeningDateTime.toLocalTime() &&
                        it.count == PeopleCount(3)
                },
            )
        }
    }
}
