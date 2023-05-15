package woowacourse.movie.view.activities.seatselection

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import io.mockk.verifyAll
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.screening.Minute
import woowacourse.movie.domain.screening.Movie
import woowacourse.movie.domain.screening.Screening
import woowacourse.movie.domain.screening.ScreeningRange
import woowacourse.movie.domain.theater.Theater
import woowacourse.movie.repository.ReservationRepository
import woowacourse.movie.repository.ScreeningRepository
import java.time.LocalDate
import java.time.LocalDateTime

class SeatSelectionPresenterTest {


    private lateinit var view: SeatSelectionContract.View

    private lateinit var screeningRepository: ScreeningRepository

    private lateinit var reservationRepository: ReservationRepository

    private val fakeTheater: Theater = Theater(5, 4)

    private val screeningId = 1L

    private val fakeScreening: Screening = Screening(
        ScreeningRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31)),
        fakeTheater,
        Movie("title", Minute(152), "summary")
    )

    private lateinit var sut: SeatSelectionPresenter

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        screeningRepository = mockk(relaxed = true)
        reservationRepository = mockk(relaxed = true)
        sut = SeatSelectionPresenter(
            view,
            screeningId,
            LocalDateTime.of(2024, 3, 1, 16, 0),
            screeningRepository,
            reservationRepository
        )
        every { screeningRepository.findById(screeningId) } returns fakeScreening
    }

    @Test
    fun `상영을 로드하면 뷰에 좌석들 UI 상태와 영화 제목과 예매 요금 0원을 설정한다`() {
        val seatsUIState = SeatsUIState.from(fakeTheater)
        every { view.setSeats(SeatsUIState.from(fakeTheater)) } just runs
        every { view.setMovieTitle(fakeScreening.movie.title) } just runs
        every { view.setReservationFee(0) } just runs

        sut.loadScreening()

        verifyAll {
            view.setSeats(seatsUIState)
            view.setMovieTitle(fakeScreening.movie.title)
            view.setReservationFee(0)
        }
    }

    @Test
    fun `좌석을 선택하면 뷰에 그에 맞게 예매 요금을 설정한다`() {
        val seatNames = setOf("A1, B1")
        every { view.setReservationFee(any()) } just runs
        sut.loadScreening()

        sut.setSelectedSeats(seatNames)

        verify { view.setReservationFee(any()) }
    }

    @Test
    fun`특정 좌석들을 선택하고 예매하면 뷰에 예매를 설정한다`() {
        val seatNames = setOf("A1, B1")
        every { view.setReservation(any()) } just runs
        sut.loadScreening()

        sut.reserve(seatNames)

        verify { view.setReservation(any()) }
    }
}