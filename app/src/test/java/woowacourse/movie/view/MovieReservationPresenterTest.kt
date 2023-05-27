package woowacourse.movie.view

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.activity.moviereservation.MovieReservationContract
import woowacourse.movie.activity.moviereservation.MovieReservationPresenter
import woowacourse.movie.view.data.DateRangeViewData
import woowacourse.movie.view.data.LocalFormattedDate
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.TheaterMovieViewData
import java.time.LocalDate
import java.time.LocalTime

class MovieReservationPresenterTest {
    private lateinit var movieViewDataMock: MovieViewData
    private lateinit var presenter: MovieReservationContract.Presenter
    private lateinit var view: MovieReservationContract.View
    private lateinit var fakeTheaterMovieViewData: TheaterMovieViewData
    private val movieDate = DateRangeViewData(LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 5))
    private val movieTimes = listOf(LocalTime.of(2, 0), LocalTime.of(5, 0))

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        movieViewDataMock = mockk(relaxed = true)
        every { movieViewDataMock.date } returns movieDate

        fakeTheaterMovieViewData = TheaterMovieViewData("", movieViewDataMock, movieTimes)

        presenter = MovieReservationPresenter(view, fakeTheaterMovieViewData, 2, 0, 0)
    }

    @Test
    fun `영화 시작 날짜와 종료 날짜 사이의 날짜들을 계산하여 스피너를 초기화한다`() {
        // given
        val slotDate = slot<List<LocalFormattedDate>>()
        every { view.initDateSpinner(any(), capture(slotDate)) } answers { nothing }

        // when
        presenter.loadDateTimeData()

        // then
        val expected = listOf(
            LocalDate.of(2023, 5, 1),
            LocalDate.of(2023, 5, 2),
            LocalDate.of(2023, 5, 3),
            LocalDate.of(2023, 5, 4),
            LocalDate.of(2023, 5, 5),
        ).map {
            LocalFormattedDate(it)
        }
        assertEquals(expected, slotDate.captured)
    }

    @Test
    fun `인원을 증가시키면 count 가 증가된다`() {
        // given
        val slotCount = slot<Int>()
        every { view.updateCount(capture(slotCount)) } answers { nothing }

        // when
        presenter.plusCount()

        // then
        assertEquals(3, slotCount.captured)
    }

    @Test
    fun `인원을 감소시키면 count 가 증가된다`() {
        // given
        val slotCount = slot<Int>()
        every { view.updateCount(capture(slotCount)) } answers { nothing }

        // when
        presenter.minusCount()

        // then
        assertEquals(1, slotCount.captured)
    }
}
